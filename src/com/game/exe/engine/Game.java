package com.game.exe.engine;

import com.game.exe.engine.util.State;

public abstract class Game {

    private State state;

    public abstract void init();
    public abstract void dispose();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        this.state.init();
    }

}
