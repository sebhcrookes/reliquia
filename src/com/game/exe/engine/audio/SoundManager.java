package com.game.exe.engine.audio;

public class SoundManager {

    public Sound backgroundSound;

    private int soundMax = 100;
    private StoredSound[] sounds = new StoredSound[soundMax];
    private Sound[] playingSounds = new Sound[soundMax];

    public SoundManager()
    {
        backgroundSound = SoundController.getSoundFromURL("/assets/sfx/jump.wav");
    }

    public void init() {

    }

    public void createSound(String soundName, String path) {
        for(int i = 0; i < sounds.length; i++) {
            if(sounds[i] == null) {
                sounds[i] = new StoredSound(soundName, SoundController.getSoundFromURL(path));
                return;
            }
        }
    }

    public void playSound(String soundName) {
        for(int i = 0; i < sounds.length; i++) {
            System.out.println(sounds[i].soundName);
            if(sounds[i].soundName == soundName) {
                sounds[i].sound.play();
                return;
            }
        }
    }

    public void stopSound(String soundName) {
        for(int i = 0; i < sounds.length; i++) {
            if(sounds[i].soundName == soundName) {
                sounds[i].sound.play();
            }
        }
    }

    public void setLoop(String soundName) {
        for(int i = 0; i < sounds.length; i++) {
            if(sounds[i].soundName == soundName) {
                sounds[i].sound.setLoop();
            }
        }
    }

    public void setLoop(String soundName, int num) {
        for(int i = 0; i < sounds.length; i++) {
            if(sounds[i].soundName == soundName) {
                sounds[i].sound.setLoop(num);
            }
        }
    }

}