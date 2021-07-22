package com.game.exe.game.entities;

public class Health {

    private GameObject obj;
    private int health = 10;

    public Health() {}

    public void init(GameObject obj, int health) {
        this.obj = obj;
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
