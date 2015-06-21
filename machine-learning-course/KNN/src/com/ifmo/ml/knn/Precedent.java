package com.ifmo.ml.knn;


import java.util.Arrays;

public class Precedent {
    private int eClass;
    private double[] features;

    public Precedent(int eClass, double[] features) {
        this.eClass = eClass;
        this.features = features;
    }

    public int geteClass() {
        return eClass;
    }

    public double[] getFeatures() {
        return features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Precedent precedent = (Precedent) o;

        return eClass == precedent.eClass;

    }

    @Override
    public String toString() {
        return "Precedent{" +
                "eClass=" + eClass +
                ", features=" + Arrays.toString(features) +
                '}';
    }
}
