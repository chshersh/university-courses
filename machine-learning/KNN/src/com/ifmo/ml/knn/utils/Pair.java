package com.ifmo.ml.knn.utils;

/**
 * author: Ruslan Sokolov
 * date: 9/17/14
 */
public class Pair<T, K> {
    private T first;
    private K second;

    public Pair(K second, T first) {
        this.second = second;
        this.first = first;
    }

    public T getFirst() {

        return first;
    }

    public K getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair pair = (Pair) o;

        return first.equals(pair.first) && second.equals(pair.second);

    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

}
