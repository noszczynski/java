package com.company;

public enum Result {

    Player("\nX"),
    Computer("\nO"),
    TIE("\nTie"),
    NONE("\nNone");

    private final String message;

    Result(String msg) {
        message = msg.trim();
    }

    @Override
    public String toString() {
        return message;
    }
}
