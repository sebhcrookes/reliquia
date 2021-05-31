package com.game.exe.game.ui;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;

public abstract class UICustomRender {

    public abstract void update(GameContainer gc, GameManager gm, float dt, UIObject uiObject);
    public abstract void render(GameContainer gc, GameManager gm, Renderer r, UIObject uiObject);

}
