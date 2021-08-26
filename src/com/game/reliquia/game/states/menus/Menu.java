package com.game.reliquia.game.states.menus;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;

public abstract class Menu {

    public abstract void init(EngineAPI api);
    public abstract void update(EngineAPI api, MainMenuState mms, float dt);
    public abstract void render(EngineAPI api, Renderer r);
    public abstract void dispose();
}
