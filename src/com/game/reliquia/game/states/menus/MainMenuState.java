package com.game.reliquia.game.states.menus;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.states.State;

public class MainMenuState extends State {

    Menu menu;
    boolean hasChanged = false;

    @Override
    public void init(EngineAPI api) {
        menu = new MainMenu();
        menu.init(api);
    }

    @Override
    public void update(EngineAPI api, float dt) {
        if(hasChanged) menu.init(api);
        menu.update(api, this, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        menu.render(api, r);
    }

    @Override
    public void dispose(EngineAPI api) {
        menu.dispose();
    }

    public void changeMenu(Menu menu) {
        this.menu.dispose();
        this.menu = menu;
        hasChanged = true;
    }
}
