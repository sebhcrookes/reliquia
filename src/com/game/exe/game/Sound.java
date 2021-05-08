package com.game.exe.game;

import com.game.exe.engine.audio.SoundClip;

public class Sound {

    public transient SoundClip music = new SoundClip("/assets/sfx/music.wav");

    public transient SoundClip jumpSound = new SoundClip("/assets/sfx/jump.wav");
    public transient SoundClip playerHurt = new SoundClip("/assets/sfx/damage.wav");
    public transient SoundClip select = new SoundClip("/assets/sfx/select.wav");
    public transient SoundClip pickupSound = new SoundClip("/assets/sfx/pickup.wav");
    public transient SoundClip explodeSound = new SoundClip("/assets/sfx/explode.wav");
    public transient SoundClip dropSound = new SoundClip("/assets/sfx/drop.wav");

    public Sound() {
        //TODO: Fix sound volume
    }
}
