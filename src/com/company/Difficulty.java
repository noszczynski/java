package com.company;

public enum Difficulty {

    Easy("Easy"),
    Normal("Normal"),
    Hard("Hard");

    private final String message;

    private Difficulty(String msg) {
        message = msg;
    }

    @Override
    public String toString() {
        return message;
    }
}
