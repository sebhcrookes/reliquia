package com.game.exe.engine.util;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;

public abstract class State {
    public abstract void init();
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer r);
    public abstract void dispose();
}