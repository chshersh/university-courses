package com.ifmo.ml.knn.kernelfunctions;

public class UniformKernelFunction implements KernelFunction {
    @Override
    public double evaluate(double r) {
        return 1.0 / 2;
    }
}
