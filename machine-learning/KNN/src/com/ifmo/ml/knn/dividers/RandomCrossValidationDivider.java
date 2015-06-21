package com.ifmo.ml.knn.dividers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomCrossValidationDivider<T> extends CrossValidationDivider<T> {
    public RandomCrossValidationDivider(List<T> elements, int k) {
        super(elements, k);
    }

    @Override
    public void divide(int i) {
        List<T> newElements = new ArrayList<>(elements);
        Collections.shuffle(newElements);

        tests = newElements.subList(0, parts);
        trains = newElements.subList(parts, elements.size());
    }
}
