package com.game.exe.engine.gui;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameState;

import javax.swing.*;

public class GUIDialog extends GUIObject {

    public GUIDialog(String title, String text) {
        JOptionPane.showMessageDialog(new JFrame(), text, title,
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {

    }
}
