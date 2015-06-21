package com.ifmo.ml.knn.kernelfunctions;

public class TriweightKernelFunction implements KernelFunction {
    @Override
    public double evaluate(double r) {
        return 35.0 * Math.pow(1 - r * r, 3) / 32.0;
    }
}
