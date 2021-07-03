package com.game.exe.engine.position;

public class Vector2 {

    private int posX, posY;

    public Vector2(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void incrementPosX(int incrementBy) {
        this.posX += incrementBy;
    }

    public void incrementPosY(int incrementBy) {
        this.posY += incrementBy;
    }
}
