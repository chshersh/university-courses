package com.ifmo.ml.yandex.recommend;

public class RateInfo {
    private long userID;
    private long itemID;
    private int rate;

    public RateInfo(long userID, long itemID, int rate) {
        this.userID = userID;
        this.itemID = itemID;
        this.rate = rate;
    }

    public long getUserID() {
        return userID;
    }

    public long getItemID() {
        return itemID;
    }

    public int getRate() {
        return rate;
    }
}
