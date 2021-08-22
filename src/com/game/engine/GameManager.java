package com.game.engine;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.states.Game;
import com.game.engine.engine.util.EngineSettings;
import com.game.engine.game.states.GameState;
import com.game.engine.game.states.menus.MainMenuState;

public class GameManager {

    private class MainGame extends Game {

        @Override
        public void init(EngineAPI api) {
            this.setState(new MainMenuState());
        }

        @Override
        public void dispose() {}
    }

    public GameManager() {}

    public void start() {
        EngineAPI api = new EngineAPI();
        api.init(new MainGame(), new EngineSettings());
        api.setClearColour(0xFF92F4FF);
        api.start();
    }
}