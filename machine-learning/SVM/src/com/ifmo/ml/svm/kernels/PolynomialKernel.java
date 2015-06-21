package com.ifmo.ml.svm.kernels;

import com.ifmo.ml.svm.Point;

import java.util.List;

import static com.ifmo.ml.svm.vectors.VectorOperations.scalarProduct;

public class PolynomialKernel implements Kernel {
    private static int d = 5;
    private static double alpha = 1.5;

    public double eval(Point x, List<Point> p, double[] a, double b) {
        double res = 0;
        for (int i = 0; i < a.length; i++) {
            res += a[i] * scalarProduct(x.vec, p.get(i).vec) * p.get(i).cl;
        }
        return Math.pow(alpha * res + b, d);
    }
}
