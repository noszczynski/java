package com.company;

public enum Result {

    Player("Player [X]"),
    Computer("Computer [O]"),
    DRAW("Draw"),
    NONE("None");

    private final String message;

    Result(String msg) {

        message = msg.trim();
    }

    @Override
    public String toString() {

        return message;
    }
}
