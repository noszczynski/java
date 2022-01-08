package com.company;

public class Main {

    public static void main(String[] args) {
        try {
            // TODO remove id from constructor - may causing errors
            Player player1 = new Player(1, "Gracz", 'x');
            Player player2 = new Player(2, "Komputer", 'o');

            Game game = new Game(player1, player2);

            game.start();
//            game.getStatus();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
