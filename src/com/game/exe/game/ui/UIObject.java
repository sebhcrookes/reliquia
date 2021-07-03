package com.game.exe.game.ui;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameState;

public abstract class UIObject {

    private String tag;
    private float posX;
    private float posY;

    public UIObject() {}

    public abstract void update(GameContainer gc, GameState gm, float dt);
    public abstract void render(GameContainer gc, GameState gm, Renderer r);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }
}
