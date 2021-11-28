package com.company;

public class Main {

    public static void main(String[] args) {
        try {
            Player player1 = new Player(1, 'x');
            Player player2 = new Player(2, 'o'); // computer

            Game game = new Game(player1, player2);

            game.start();
            game.getStatus();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
