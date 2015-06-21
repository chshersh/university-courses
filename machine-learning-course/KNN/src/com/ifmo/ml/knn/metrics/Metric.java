package com.ifmo.ml.knn.metrics;

import com.ifmo.ml.knn.Precedent;

@FunctionalInterface
public interface Metric {
    double countMetric(Precedent p1, Precedent p2);
}
