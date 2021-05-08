package com.game.exe.engine.audio;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundController {

    public SoundController() {}

    public static Sound getSoundFromURL(String url)
    {
        Clip c;
        File soundFile = new File(File.class.getResource(url).getFile());
        try(AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)){
            c = AudioSystem.getClip();
            c.open(audioIn);
            return new Sound(c);
        }catch(Exception e)
        {}

        return null;
    }
}