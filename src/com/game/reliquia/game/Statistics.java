package com.game.reliquia.game;

public class Statistics {

    private int timesPlayed;
    private long durationPlayed;

    public Statistics(int timesPlayed) {
        this.timesPlayed = timesPlayed + 1;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public long getDurationPlayed() {
        return durationPlayed;
    }

    public void setDurationPlayed(long durationPlayed) {
        this.durationPlayed = durationPlayed;
    }
}
