package com.game.exe.engine.gui;


import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.position.Vector2;
import com.game.exe.game.GameState;
import com.game.exe.game.entities.CustomEntityData;
import com.game.exe.game.entities.GameObject;

public abstract class GUIObject {


    protected String tag;
    public Vector2 position;

    public abstract void update(GameContainer gc, float dt);

    public abstract void render(GameContainer gc, Renderer r);

}
