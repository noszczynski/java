package com.company;

public class Move {
    int column = 0, row = 0;
    boolean ok = false;

    Move(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public void setOk(boolean status) {
        ok = status;
    }

    public boolean isOk() {
        return this.ok;
    }
}
