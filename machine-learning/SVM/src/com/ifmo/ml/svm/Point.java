package com.ifmo.ml.svm;

import java.util.Arrays;

public class Point {
    public double[] vec;
    public int cl;

    public Point(double[] vec, int cl) {
        this.vec = vec;
        this.cl = cl;
    }

    @Override
    public String toString() {
        return "Point{" +
                "vec=" + Arrays.toString(vec) +
                ", cl=" + cl +
                '}';
    }
}
