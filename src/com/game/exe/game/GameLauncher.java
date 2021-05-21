package com.game.exe.game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
            String message = e.toString();
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), message, "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
            gm.save();
            System.exit(0);
        }
    }
}