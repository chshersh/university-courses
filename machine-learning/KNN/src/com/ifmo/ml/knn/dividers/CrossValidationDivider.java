package com.ifmo.ml.knn.dividers;

import java.util.ArrayList;
import java.util.List;

public class CrossValidationDivider<T> implements Divider<T> {
    protected List<T> elements;
    protected int k;
    protected int parts;
    protected List<T> trains, tests;

    public CrossValidationDivider(List<T> elements, int k) {
        this.elements = elements;
        this.k = k;
        this.parts = elements.size() / k;
    }

    @Override
    public int iterations() {
        return k;
    }

    @Override
    public int getParts() {
        return parts;
    }

    @Override
    public void divide(int i) {
        tests = elements.subList(i * parts, (i + 1) * parts);
        trains = new ArrayList<>(elements.subList(0, i * parts));
        trains.addAll(elements.subList((i + 1) * parts, elements.size()));
    }

    @Override
    public List<T> getTrainingSamples() {
        return trains;
    }

    @Override
    public List<T> getTestingSamples() {
        return tests;
    }
}
