package com.game.engine.game.states.menus;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;

public abstract class Menu {

    public abstract void init(EngineAPI api);
    public abstract void update(EngineAPI api, MainMenuState mms, float dt);
    public abstract void render(EngineAPI api, Renderer r);
    public abstract void dispose();
}
