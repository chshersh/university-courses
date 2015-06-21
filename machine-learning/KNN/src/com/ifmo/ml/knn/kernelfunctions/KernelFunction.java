package com.ifmo.ml.knn.kernelfunctions;

@FunctionalInterface
public interface KernelFunction {
    double evaluate(double r);
}
