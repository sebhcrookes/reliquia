package com.game.engine.game;

public class Statistics {

    private int timesPlayed;

    public Statistics(int timesPlayed) {
        this.timesPlayed = timesPlayed + 1;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }
}
