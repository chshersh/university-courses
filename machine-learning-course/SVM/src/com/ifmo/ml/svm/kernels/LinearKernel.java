package com.ifmo.ml.svm.kernels;

import com.ifmo.ml.svm.Point;
import static com.ifmo.ml.svm.vectors.VectorOperations.*;

import java.util.List;

public class LinearKernel implements Kernel {
    public double eval(Point x, List<Point> p, double[] a, double b) {
        double res = 0;
        for (int i = 0; i < a.length; i++) {
            res += a[i] * scalarProduct(x.vec, p.get(i).vec) * p.get(i).cl;
        }
        return res + b;
    }
}
