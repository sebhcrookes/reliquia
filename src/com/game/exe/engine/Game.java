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
        if(this.state != null)
            this.state.dispose();
        this.state = state;
        this.state.init();
    }

}
