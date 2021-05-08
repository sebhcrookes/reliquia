package com.game.exe.game;

import java.io.Serializable;

public class Random implements Serializable {

    public java.util.Random random = new java.util.Random();

    public Random() {

    }

    public int generate(int low, int high) {
        return random.nextInt((high-low) + low);
    }

}
