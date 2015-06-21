package com.ifmo.ml.knn.metrics;

import com.ifmo.ml.knn.Precedent;

public class EuclidMetric implements Metric {
    @Override
    public double countMetric(Precedent p1, Precedent p2) {
        double[] f1 = p1.getFeatures(), f2 = p2.getFeatures();
        double m = 0;
        for (int i = 0; i < f1.length; i++) {
            m += (f1[i] - f2[i]) * (f1[i] - f2[i]);
        }
        return Math.sqrt(m);
    }
}
