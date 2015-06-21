package com.ifmo.ml.svm.kernels;


import com.ifmo.ml.svm.Point;

import java.util.List;

public interface Kernel {
    public double eval(Point x, List<Point> p, double[] a, double b);
}
