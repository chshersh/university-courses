package com.ifmo.ml.svm.vectors;


import java.util.stream.DoubleStream;

public class VectorOperations {
    public static double scalarProduct(double[] x, double[] y) {
        double res = 0;
        for (int i = 0; i < x.length; i++) {
            res += x[i] * y[i];
        }
        return res;
    }

    public static double[] mulVecOnScalar(double[] vec, double s) {
        return DoubleStream.of(vec).map(v -> v * s).toArray();
    }

    public static void sum2Vectors(double[] a, double[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i] += b[i];
        }
    }
}
