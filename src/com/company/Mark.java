package com.company;

public enum Mark {

    Player('X'),
    Computer('O'),
    EMPTY(' ');

    private final char message;

    Mark(char msg) {

        message = msg;
    }

    public boolean isEmpty() {

        return this == EMPTY;
    }

    public boolean isMarked() {

        return !isEmpty();
    }

    public char getMark() {

        return this.message;
    }

    @Override
    public String toString() {

        return String.valueOf(message);
    }
}
