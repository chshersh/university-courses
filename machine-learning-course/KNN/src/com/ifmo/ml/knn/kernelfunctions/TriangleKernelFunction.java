package com.ifmo.ml.knn.kernelfunctions;

public class TriangleKernelFunction implements KernelFunction {
    @Override
    public double evaluate(double r) {
        return 1 - r;
    }
}
