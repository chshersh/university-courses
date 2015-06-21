package com.ifmo.ml.yandex.recommend;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SVDRatingPredictor {

    public class DataHolder {
        private List<RateInfo> ratings;

        public DataHolder(String filename) throws IOException {
            ratings = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line = br.readLine(); // skip first column with fields names

                while((line = br.readLine()) != null) {
                    String[] info = line.split(",");
                    RateInfo rateInfo = new RateInfo(
                            Long.parseLong(info[0]), Long.parseLong(info[1]), Integer.parseInt(info[2])
                    );

                    ratings.add(rateInfo);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private DataHolder trainData;
    private DataHolder validationData;
    private List<RateInfo> trains;
    private Map<Long, Integer> users; // rows by users
    private Map<Long, Integer> items; // columns by items
    private LearnThread bestParamsThread;

    // optimizing parameters
    private double mu;
    private static final int[] featureSet = {5, 10, 15, 40};

    public SVDRatingPredictor(String trainDataFileName, String validationDataFileName) throws IOException {
        this.trainData = new DataHolder(trainDataFileName);
        this.validationData = new DataHolder(validationDataFileName);
        this.trains = trainData.ratings;

        fillTables();
    }

    private void fillTables() {
        this.users = new HashMap<>();
        this.items = new HashMap<>();

        int sum = 0;
        for (RateInfo info : trains) {
            if (!users.containsKey(info.getUserID())) {
                users.put(info.getUserID(), users.size());
            }

            if (!items.containsKey(info.getItemID())) {
                items.put(info.getItemID(), items.size());
            }

            sum += info.getRate();
        }

        this.mu = 1.0 * sum / trains.size();
    }

    private double dotProduct(double[] user, double[] item) {
        return IntStream.range(0, user.length).mapToDouble(i -> user[i] * item[i]).sum();
    }

    private class LearnThread implements Runnable {
        private double lambda;
        private int featNum;
        private double gam = 0.001, lam = 0.02;

        private double mu;
        private double[] buser;
        private double[] bitem;
        private double[][] userFeatures;
        private double[][] itemFeatures;

        public LearnThread(double mu, double lambda, int featNum) {
            this.lambda = lambda;
            this.featNum = featNum;

            this.mu = mu;

            this.buser = new double[users.size()];
            this.bitem = new double[items.size()];

            this.userFeatures = new double[users.size()][featNum];
            this.itemFeatures = new double[items.size()][featNum];

            // getting for users random vectors with elements in range (0, 1/n)
            Random rnd = new Random();
            double rangeMin = 0;
            double rangeMax = 1.0 / featNum;

            for (double[] userVector : userFeatures) {
                for (int i = 0; i < userVector.length; i++) {
                    userVector[i] = rangeMin + (rangeMax - rangeMin) * rnd.nextDouble();
                }
            }

            for (double[] itemVector : itemFeatures) {
                for (int i = 0; i < itemVector.length; i++) {
                    itemVector[i] = rangeMin + (rangeMax - rangeMin) * rnd.nextDouble();
                }
            }
        }

        @Override
        public void run() {
            System.out.println("Current lambda: " + lambda);
            System.out.println("Current features number: " + featNum);

            double error = countErrorFunction(trains, true), oldError = 0;
            double terminalEps = 1e-6;
            double smallImprove = 5;

            while (Math.abs(error - oldError) >= terminalEps) {
                oldError = error;

                for (RateInfo info : trains) {
                    int u = users.get(info.getUserID());
                    int i = items.get(info.getItemID());
                    double[] user = userFeatures[u];
                    double[] item = itemFeatures[i];

                    double predictedRate = this.mu + buser[u] + bitem[i] + dotProduct(user, item);
                    double eui = info.getRate() - predictedRate;

                    buser[u] += gam * (eui - lam * buser[u]);
                    bitem[i] += gam * (eui - lam * bitem[i]);

                    for (int j = 0; j < featNum; j++) {
                        double oldUserFeat = user[j];
                        user[j] += gam * (eui * item[j] - lam * user[j]);
                        item[j] += gam * (eui * oldUserFeat - lam * item[j]);
                    }
                }

                error = countErrorFunction(trains, true);

                if (Math.abs(error - oldError) < smallImprove) {
                    gam *= 0.6;
                    smallImprove *= 0.9;
                }

                System.out.println("Error: " + error);
                System.out.println("Dif: " + Math.abs(error - oldError));
            }
        }

        private double countErrorFunction(List<RateInfo> ratings, boolean notRMSE) {
            double error = 0;
            for (RateInfo info : ratings) {
                double predictedRate = this.mu, bu = 0, bi = 0, userSum = 0, itemSum = 0;
                double[] user = null, item = null;
                boolean hasUser = false, hasItem = false;

                if (users.containsKey(info.getUserID())) {
                    hasUser = true;
                    int u = users.get(info.getUserID());
                    bu = buser[u] * buser[u];
                    user = userFeatures[u];
                    userSum = Arrays.stream(user).map(b -> b * b).sum();
                    predictedRate += buser[u];
                }

                if (items.containsKey(info.getItemID())) {
                    hasItem = true;
                    int i = items.get(info.getItemID());
                    bi = bitem[i] * bitem[i];
                    item = itemFeatures[i];
                    itemSum = Arrays.stream(item).map(b -> b * b).sum();
                    predictedRate += bitem[i];
                }

                if (hasUser && hasItem) {
                    predictedRate += dotProduct(user, item);
                }

                double r = info.getRate() - predictedRate;

                error += r * r;
                if (notRMSE) {
                    error += lambda * (bi + bu + userSum + itemSum);
                }
            }

            if (notRMSE) {
                return error;
            }

            return Math.sqrt(error / ratings.size());
        }

        public long predictRating(long userID, long itemID) {
            double predictedRate = this.mu;
            double[] user = null, item = null;
            boolean hasUser = false, hasItem = false;

            if (users.containsKey(userID)) {
                hasUser = true;
                int u = users.get(userID);
                user = userFeatures[u];
                predictedRate += buser[u];
            }

            if (items.containsKey(itemID)) {
                hasItem = true;
                int i = items.get(itemID);
                item = itemFeatures[i];
                predictedRate += bitem[i];
            }

            if (hasUser && hasItem) {
                predictedRate += dotProduct(user, item);
            }

            long r = Math.round(predictedRate);
            if (r < 1) {
                r = 1;
            } else if (r > 5) {
                r = 5;
            }

            return r;
        }
    }

    public void learn() throws InterruptedException {
        List<LearnThread> learnThreads = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        double lambda = 0.03;
        while (lambda < 1) {
            for (int fNum : featureSet) {
                LearnThread learnThread = new LearnThread(mu, lambda, fNum);

                learnThreads.add(learnThread);
                threads.add(new Thread(learnThread));
                threads.get(learnThreads.size() - 1).start();

            }

            lambda *= 3;
        }

        double minRMSE = 2000000;
        double bestLambda = 0.03;
        int bestFeatureNum = 5;
        for (int i = 0; i < learnThreads.size(); i++) {
            threads.get(i).join();
            LearnThread lt = learnThreads.get(i);

            double res = lt.countErrorFunction(validationData.ratings, false);

            if (res < minRMSE) {
                minRMSE = res;
                bestLambda = lt.lambda;
                bestFeatureNum = lt.featNum;
            }
        }

        trains = Stream
                .concat(trainData.ratings.stream(), validationData.ratings.stream())
                .collect(Collectors.<RateInfo>toList());
        fillTables();

        bestParamsThread = new LearnThread(mu, bestLambda, bestFeatureNum);
        Thread bestThread = new Thread(bestParamsThread);
        bestThread.start();
        bestThread.join();

        System.out.println("Best Lambda: " + bestParamsThread.lambda);
        System.out.println("Best features: " + bestParamsThread.featNum);
        System.out.println("Minimal RMSE: " + minRMSE);
    }

    public void passTests(String testsFileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(testsFileName));
             BufferedWriter bw = new BufferedWriter(new FileWriter("submission.csv"))
        ) {
            String line = br.readLine(); // skip title
            StringBuilder ans = new StringBuilder("id,rating\n");

            while ((line = br.readLine()) != null) {
                String[] test = line.split(",");
                int id = Integer.parseInt(test[0]);
                long user = Long.parseLong(test[1]);
                long item = Long.parseLong(test[2]);

                ans.append(id).append(',').append(bestParamsThread.predictRating(user, item)).append('\n');
            }

            bw.write(ans.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
