package com.game.exe.game.entities;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.position.Vector2;
import com.game.exe.game.GameState;

public abstract class GameObject extends Physics {

    protected String tag;
    public Vector2 position;
    protected int width, height;
    protected boolean dead = false;
    protected boolean isItem = false;
    protected CustomEntityData customEntityData = new CustomEntityData();
    protected int tileX, tileY;

    public abstract void update(GameContainer gc, GameState gm, float dt);
    public abstract void render(GameContainer gc, Renderer r);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getTileX() { return this.tileX; }

    public int getTileY() { return this.tileY; }

    public void setTileX(int tileX) { this.tileX = tileX; }

    public void setTileY(int tileY) { this.tileY = tileY; }

    public float getOffX() { return this.offX; }

    public float getOffY() { return this.offY; }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public void setOffX(float offX) { this.offX = offX; }

    public CustomEntityData getCustomEntityData() {
        return this.customEntityData;
    }

    public void setCustomEntityData(CustomEntityData customEntityData) { this.customEntityData = customEntityData; }
}