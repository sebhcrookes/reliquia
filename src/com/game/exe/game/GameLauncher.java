package com.game.exe.game;

public class GameLauncher {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        GameManager gm = new GameManager();
        try {
            gm.startGame();
        } catch (Exception e) {
            System.out.println("There was an error during runtime. Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}