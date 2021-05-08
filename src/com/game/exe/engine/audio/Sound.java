package com.game.exe.engine.audio;

import javax.sound.sampled.Clip;

public class Sound {

    private final Clip soundClip;

    public Sound(Clip c)
    {
        soundClip = c;
    }

    public void play()
    {
        System.out.println("Played");
        soundClip.setMicrosecondPosition(0);
        soundClip.start();
    }

    void setLoop(int num) {
        soundClip.loop(num);
    }

    void setLoop() {
        soundClip.loop(-1);
    }

    public boolean isPlaying() {
        try{
            return soundClip.isRunning();
        }catch(Exception ignored) {}
        return true;
    }

}