package com.ifmo.ml.bsc;


import com.ifmo.ml.bsc.utils.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BayesClassifier {
    private List<EmailMessage> emails;
    private Map<String, Integer> spamFreqs, hamFreqs;
    private int totalSpam, totalHam, unique;
    double spamTotalProb, hamTotalProb;

    public BayesClassifier() throws IOException {
        this.emails = new ArrayList<>();
        this.spamFreqs = new HashMap<>();
        this.hamFreqs = new HashMap<>();

        Path start = Paths.get("./data");
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                List<String> head = new ArrayList<>();
                List<String> body = new ArrayList<>();
                boolean isSpam = file.toString().contains("spmsg");

                try (BufferedReader br = Files.newBufferedReader(file)) {
                    String subject = br.readLine();
                    head.addAll(Arrays.asList(subject.substring("Subject: ".length()).split("\\s")));
                    head = head.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());

                    String line;
                    while ((line = br.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            body.add(st.nextToken());
                        }
                    }

                    emails.add(new EmailMessage(
                            Stream.concat(head.stream(), body.stream()).collect(Collectors.toList()),
                            isSpam)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                if (e == null) {
                    return FileVisitResult.CONTINUE;
                } else {
                    // directory iteration failed
                    throw e;
                }
            }
        });
    }

    private void putInMap(Map<String, Integer> map, String word) {
        if (map.containsKey(word)) {
            map.put(word, map.get(word) + 1);
        } else {
            map.put(word, 1);
        }
    }

    private void countWordsFreq(List<EmailMessage> data) {
        int totalSpamCnt = 0, totalHamCnt = 0;
        for (EmailMessage email : data) {
            if (email.isSpam()) {
                totalSpamCnt++;
            } else {
                totalHamCnt++;
            }

            for (String w : email.getBody()) {
                if (email.isSpam()) {
                    putInMap(spamFreqs, w);
                } else {
                    putInMap(hamFreqs, w);
                }
            }
        }

        totalSpam = spamFreqs.values().stream().mapToInt(i -> i).sum();
        totalHam = hamFreqs.values().stream().mapToInt(i -> i).sum();

        spamTotalProb = 1.0 * totalSpamCnt / emails.size();
        hamTotalProb = 1.0 * totalHamCnt / emails.size();

        unique = new HashSet<>(
                    Stream.concat(
                        spamFreqs.keySet().stream(),
                        hamFreqs.keySet().stream()
                    ).collect(Collectors.toList())
        ).size();
    }

    private double wordProbability(Map<String, Integer> cntTable, String word, int total) {
        int wc = 0;
        if (cntTable.containsKey(word)) {
            wc = cntTable.get(word);
        }

        return (wc + 1.0 / unique) / (total + 1);
    }

    private double findMeasure(List<EmailMessage> tests, double b) {
        int[][] tm = new int[2][2];
        for (EmailMessage email : tests) {
            double spamProb = Math.log(spamTotalProb);
            double hamProb = Math.log(hamTotalProb);

            for (String w : email.getBody()) {
                spamProb += Math.log(wordProbability(spamFreqs, w, totalSpam));
                hamProb += Math.log(wordProbability(hamFreqs, w, totalHam));
            }

            int resClass = (hamProb > spamProb) ? 0 : 1;
            int trueClass = (email.isSpam()) ? 1 : 0;

            tm[resClass][trueClass]++;
        }

        if (tm[1][1] == 0) {
            System.out.println("NAN");
            return 0;
        }

        double precision = 1.0 * tm[1][1] / (tm[1][1] + tm[1][0]),
               recall    = 1.0 * tm[1][1] / (tm[1][1] + tm[0][1]);

        return (1 + b * b) * precision * recall / (b * b * precision + recall);
    }

    public Pair<Double, Double> learn() {
        Collections.shuffle(emails);

        List<EmailMessage> tests = emails.subList(0, emails.size() / 5);
        List<EmailMessage> trains = emails.subList(emails.size() / 5, emails.size());

        countWordsFreq(trains);
        return new Pair<>(findMeasure(tests, 1), findMeasure(trains, 1));
    }
}
