package com.ifmo.ml.reg;

/**
 * Created by ruslan on 11/13/14
 */
public class FlatInfo {
    private int rooms;
    private int square;
    private int cost;

    public FlatInfo(int rooms, int square, int cost) {
        this.rooms = rooms;
        this.square = square;
        this.cost = cost;
    }

    public int getRooms() {
        return rooms;
    }

    public int getSquare() {
        return square;
    }

    public int getCost() {
        return cost;
    }
}
