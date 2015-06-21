package com.ifmo.ml.knn.kernelfunctions;

public class GaussianKernelFunction implements KernelFunction {
    @Override
    public double evaluate(double r) {
        return Math.exp(-r * r / 2) / Math.sqrt(2 * Math.PI);
    }
}
