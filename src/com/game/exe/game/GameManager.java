package com.game.exe.game;

import com.game.exe.engine.util.EngineSettings;
import com.game.exe.engine.Game;
import com.game.exe.engine.GameContainer;

public class GameManager extends Game {

    @Override
    public void init() {
        this.setState(new MenuState());
    }

    @Override
    public void dispose() {

    }
}
