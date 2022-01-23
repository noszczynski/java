package com.company;

public enum Mark {

    Player("X"),
    Computer("O"),
    EMPTY(" ");

    private final String message;

    Mark(String msg) {
        message = msg;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Override
    public String toString() {
        return message;
    }
}
