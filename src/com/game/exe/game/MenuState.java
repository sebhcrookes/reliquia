package com.game.exe.game;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gui.GUIButton;
import com.game.exe.engine.position.Vector2;
import com.game.exe.engine.util.State;

public class MenuState extends State {

    GUIButton startBtn;
    private static boolean start = false;

    @Override
    public void init() {
        startBtn = new GUIButton();
        startBtn.setText("Start game!");
        startBtn.setBounds(new Vector2(1, 0), 0, 0);
        startBtn.pack();
        startBtn.setClickConsumer(MenuState::startGame);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(start)
            gc.getGame().setState(new GameState());
        startBtn.update(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        startBtn.render(gc, r);
    }

    @Override
    public void dispose() {

    }

    public static void startGame(Object o) {
        start = true;
    }
}
