package com.game.engine.game.states.menus;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.states.State;

public class MainMenuState extends State {

    Menu menu;

    @Override
    public void init(EngineAPI api) {
        menu = new MainMenu();
    }

    @Override
    public void update(EngineAPI api, float dt) {
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
    }
}
