package com.company;

public class Player {
    int id;
    char sign = '0';
    String name = "";

    public Player(int id, String name, char sign) {
        if (id == 0) {
//                throw new Exception("id cannot be 0");
        }

        this.id = id;
        this.name = name;
        this.sign = sign;
    }

    public int getId() {
        return this.id;
    }

    public char getSign() {
        return this.sign;
    }

    public String getName() {
        return this.name;
    }
}
