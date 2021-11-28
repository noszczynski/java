package com.company;

public class Player {
    int id;
    char sign = '0';

    public Player(int id, char sign) {
        if (id == 0) {
//                throw new Exception("id cannot be 0");
        }

        this.id = id;
        this.sign = sign;
    }

    public int getId() {
        return this.id;
    }

    public char getSign() {
        return this.sign;
    }
}
