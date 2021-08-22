package com.game.engine.game;

import java.util.Random;

public class GlobalRandom {

    private static Random r = new Random();

    public static int nextInt(int bound) {
        return r.nextInt(bound);
    }

    public static double nextDouble() {
        return r.nextDouble();
    }
}
