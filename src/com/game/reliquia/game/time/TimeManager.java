package com.game.reliquia.game.time;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.game.states.GameState;

public class TimeManager {

    StopWatch sw;
    long offset = 0;

    public TimeManager(GameState gs) {
        sw = new StopWatch();
        sw.start();

        offset = gs.getStatistics().getDurationPlayed();
    }

    public void update(EngineAPI api, GameState gs, float dt) {
        gs.getStatistics().setDurationPlayed(offset + sw.getTimeInSeconds());
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
