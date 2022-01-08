package com.company;

public class Move {
    char column;
    int row;

    Move(char column, int row) {
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }
}
