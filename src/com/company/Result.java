package com.company;

public enum Result {

    Player("\nPlayer [X]"),
    Computer("\nComputer [O]"),
    DRAW("\nDraw [X/O]"),
    NONE("\nNone [-/-]");

    private final String message;

    Result(String msg) {

        message = msg.trim();
    }

    @Override
    public String toString() {

        return message;
    }
}
