package com.ifmo.ml.svm;

import com.ifmo.ml.svm.dividers.CrossValidationDivider;
import com.ifmo.ml.svm.dividers.Divider;
import com.ifmo.ml.svm.kernels.Kernel;
import com.ifmo.ml.svm.kernels.LinearKernel;
import com.ifmo.ml.svm.kernels.PolynomialKernel;
import com.ifmo.ml.svm.utils.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static com.ifmo.ml.svm.vectors.VectorOperations.*;

public class SVMClassifier {
    private static final int KD_FOLD_CONST = 5;
    private static final Kernel kernel = new PolynomialKernel();

    private List<Point> points;

    private double[] alphas;
    private double beta;

    public SVMClassifier(String filename) {
        points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] pts = line.split(" ");
                int y = Integer.parseInt(pts[2]);
                if (y == 0) y = -1;

                points.add(new Point(
                        new double[]{Double.parseDouble(pts[0]), Double.parseDouble(pts[1])}, y
                ));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private double E(Point x, List<Point> p, double[] a, double b) {
        return kernel.eval(x, p, a, b) - x.cl;
    }

    private boolean SMO(List<Point> p, double[] a, double c) {
        Random rnd = new Random();

        // parameters
        double eps = 1e-3;
        double tol = 1e-5;
        int maxPasses = 5;

        // output
        double b = 0;
        int passes = 0;

        while (passes < maxPasses) {
            int numChangedAlphas = 0;
            for (int i = 0; i < p.size(); i++) {
                Point x = p.get(i);
                double ei = E(x, p, a, b);

                if ((x.cl * ei < -tol && a[i] < c) || (x.cl * ei > tol && a[i] > 0)) {
                    int j = rnd.nextInt(p.size());
                    while (j == i) j = rnd.nextInt(p.size());

                    Point y = p.get(j);
                    double ej = E(y, p, a, b);
                    double oldAI = a[i], oldAJ = a[j];

                    // cnt boundaries
                    double L, H;
                    if (p.get(i).cl == p.get(j).cl) {
                        L = Math.max(0, a[i] + a[j] - c);
                        H = Math.min(c, a[i] + a[j]);
                    } else {
                        L = Math.max(0, a[j] - a[i]);
                        H = Math.min(c, c + a[j] - a[i]);
                    }

                    if (H <= L - eps) continue;

                    double eta = 2 * scalarProduct(x.vec, y.vec)
                                - scalarProduct(x.vec, x.vec)
                                - scalarProduct(y.vec, y.vec);

                    if (eta >= 0) continue;

                    a[j] = a[j] - (y.cl * (ei - ej)) / eta;
                    if (a[j] > H) a[j] = H;
                    else if (a[j] < L) a[j] = L;

                    if (Math.abs(a[j] - oldAJ) < eps) continue;

                    a[i] = a[i] + x.cl * y.cl * (oldAJ - a[j]);

                    double b1 = b - ei - x.cl * (a[i] - oldAI) * scalarProduct(x.vec, x.vec)
                                - y.cl * (a[j] - oldAJ) * scalarProduct(x.vec, y.vec),
                           b2 = b - ej - x.cl * (a[i] - oldAI) * scalarProduct(x.vec, y.vec)
                                - y.cl * (a[j] - oldAJ) * scalarProduct(y.vec, y.vec);

                    if (0 < a[i] && a[i] < c)
                        b = b1;
                    else if (0 < a[j] && a[j] < c)
                        b = b2;
                    else
                        b = (b1 + b2) / 2;

                    numChangedAlphas++;
                }
            }
            passes = (numChangedAlphas == 0 ? passes + 1 : 0);
        }

        beta = b;

        for (int i = 0; i < a.length; i++) {
            if (a[i] <= eps) {
                if (p.get(i).cl * kernel.eval(p.get(i), p, a, b) < 1)
                    return false;
            } else if (a[i] >= c - eps) {
                if (p.get(i).cl * kernel.eval(p.get(i), p, a, b) > 1)
                    return false;
            } else {
                if (1 - eps <= p.get(i).cl * kernel.eval(p.get(i), p, a, b) ||
                        p.get(i).cl * kernel.eval(p.get(i), p, a, b) <= 1 + eps)
                    return false;
            }
        }

        return true;
    }

    private void SMOSolve(List<Point> p) {
        double c = 0.001;
        double res = 0;
        double maxMeasure = 0;

        Divider<Point> divider = new CrossValidationDivider<>(p, KD_FOLD_CONST);

        while (c < 100) {
            double aveMeasure = 0;

            for (int i = 0; i < divider.iterations(); i++) {
                divider.divide(i);
                List<Point> trains = divider.getTrainingSamples(),
                            tests  = divider.getTestingSamples();

                double[] a = new double[trains.size()];
                Arrays.fill(a, 0);
                SMO(trains, a, c);
                alphas = a;

                aveMeasure += findMeasure(trains, tests);
            }

            if (maxMeasure < aveMeasure) {
                maxMeasure = aveMeasure;
                res = c;
            }

            c *= 2;
        }

        System.out.println(res);
        alphas = new double[p.size()];
        SMO(p, alphas, res);
    }

    private Pair<Double, Double> getCoefs(List<Point> pts) {
        SMOSolve(pts);
        double[] w = {0, 0};
        for (int i = 0; i < pts.size(); i++) {
            sum2Vectors(w, mulVecOnScalar(pts.get(i).vec, alphas[i] * pts.get(i).cl));
        }

        List<Double> frees = new ArrayList<>();
        for (int i = 0; i < alphas.length; i++) {
            if (alphas[i] > 0) {
                frees.add(scalarProduct(w, pts.get(i).vec) - pts.get(i).cl);
            }
        }
        Collections.sort(frees);
        double w0 = frees.get(frees.size() / 2);

        // 0 = w[0] * x + w[1] * y + w0
        // y = k * x + b

        double k = w[0] / w[1],
               b = w0 / w[1];

        return new Pair<>(k, b);
    }

    private double findMeasure(List<Point> trains, List<Point> tests) {
        int[][] tm = new int[2][2];
        for (Point p : tests) {
            double f = p.cl * kernel.eval(p, trains, alphas, beta);

            int resClass = (p.cl == - 1) ? 0 : 1;
            int trueClass = (f > 0) ? 1 : 0;

            tm[resClass][trueClass]++;
        }

        if (tm[1][1] == 0) {
            //System.err.println("NAN occurred");
            return 0;
        } else {
            //System.out.println("tm = " + Arrays.toString(tm[0]) + " " + Arrays.toString(tm[1]));
        }

        double precision = 1.0 * tm[1][1] / (tm[1][1] + tm[1][0]),
                recall   = 1.0 * tm[1][1] / (tm[1][1] + tm[0][1]);

        return 2 * precision * recall / (precision + recall);
    }

    public Pair<Double, Double> learn() {
        Collections.shuffle(points);

        Pair<Double, Double> coef = getCoefs(points);
        //double k = coef.getFirst(), b = coef.getSecond();

        System.out.println("measure = " + findMeasure(points, points));

        return coef;
    }
}
