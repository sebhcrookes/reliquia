package com.game.engine.game.blocks;

public class Block {

    private String name;
    private int colourCode;
    private int number;
    private boolean doesCollide;

    public Block(String name, int colourCode, int number, boolean doesCollide) {
        this.name = name;
        this.colourCode = colourCode;
        this.number = number;
        this.doesCollide = doesCollide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColourCode() {
        return colourCode;
    }

    public void setColourCode(int colourCode) {
        this.colourCode = colourCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean doesCollide() {
        return doesCollide;
    }

    public void setDoesCollide(boolean doesCollide) {
        this.doesCollide = doesCollide;
    }
}
