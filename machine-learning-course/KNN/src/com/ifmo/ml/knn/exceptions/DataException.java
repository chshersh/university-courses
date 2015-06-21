package com.ifmo.ml.knn.exceptions;

/**
 * author: Ruslan Sokolov
 * date: 9/17/14
 */
public class DataException extends RuntimeException {

    public DataException() {
        super();
    }

    public DataException(String message) {
        super(message);
    }
}
