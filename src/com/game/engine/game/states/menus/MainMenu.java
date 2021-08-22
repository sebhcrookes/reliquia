package com.game.engine.game.states.menus;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;

public class MainMenu extends Menu {

    String titleText = "game.exe V1.0";

    @Override
    public void init(EngineAPI api) {
        api.setClearColour(0xFF18191A);
    }

    @Override
    public void update(EngineAPI api, MainMenuState mms, float dt) {
        api.getGame().setState(new GameState());
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawText(titleText, api.getWidth() / 2 - (r.getFont().getTextLength(titleText) / 2), 50, 0xFFFFFFFF);
    }

    @Override
    public void dispose() {

    }
}
