package com.game.exe.game;

import com.game.engine.engine.util.EngineSettings;
import com.game.exe.engine.Game;
import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gui.GUIButton;
import com.game.exe.engine.position.Vector2;
import com.game.exe.engine.util.State;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameLauncher {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        try {
            GameContainer gc = new GameContainer(new GameManager(), new EngineSettings());
            gc.start();
        } catch (Exception e) {
            System.out.println("There was an error during runtime. Exception: " + e.toString());
            e.printStackTrace();
            String message = e.toString();
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), message, "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}