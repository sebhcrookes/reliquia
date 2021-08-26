package com.game.reliquia;

import com.game.reliquia.engine.audio.AudioClip;
import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.states.Game;
import com.game.reliquia.engine.util.EngineSettings;
import com.game.reliquia.game.states.menus.MainMenuState;

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

        // Initialise Audio Clips
        AudioClip.addSound("invalid", "/gui/invalid_selection.wav", 0.5F);
        AudioClip.addSound("select", "/gui/select.wav", 0.5F);

        AudioClip.addSound("player_jump", "/objects/player/sfx/jump.wav", 0.5F);
        AudioClip.addSound("entity_death", "/objects/player/sfx/death.wav", 0.5F);

        AudioClip.addSound("item_drop", "/items/sfx/drop.wav", 0.5F);
        AudioClip.addSound("item_pickup", "/items/sfx/pickup.wav", 0.5F);

        EngineAPI api = new EngineAPI();
        api.init(new MainGame(), new EngineSettings());
        api.start();
    }
}