package com.ifmo.ml.bsc;


import java.util.List;

public class EmailMessage {
    private List<String> body;
    private boolean isSpam;

    public EmailMessage(List<String> body, boolean isSpam) {
        this.body = body;
        this.isSpam = isSpam;
    }

    public List<String> getBody() {
        return body;
    }

    public boolean isSpam() {
        return isSpam;
    }
}
