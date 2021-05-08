package com.game.exe.game.level;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameManager;

public class LevelWeather {

    private GameContainer gc;
    private GameManager gm;
    private LevelManager lm;

    public static final int CLEAR = 0;
    public static final int RAIN = 1;
    public static final int SNOW = -1;

    private int weatherIntensity = 0;
    private String weatherType = "clear";
    private int bgParticle = 0;

    public LevelWeather(GameContainer gc, GameManager gm, LevelManager lm) {
        this.gc = gc;
        this.gm = gm;
        this.lm = lm;
    }

    public void set(int weatherType) {
        System.out.println(weatherType);
        switch(weatherType) {
            case RAIN:
                this.weatherType = "rain";
                weatherIntensity = 3;
                return;
            case CLEAR:
                this.weatherType = "clear";
                weatherIntensity = 0;
                return;
            case SNOW:
                this.weatherType = "snow";
                weatherIntensity = 3;
                return;
        }
    }

    public void update(GameContainer gc, float dt) {
        if(bgParticle >= weatherIntensity && weatherIntensity != 0) {
            bgParticle = 0;

            gm.particles.createParticle(weatherType,gm.random.generate((int)gm.camera.getOffX(), (int)gm.camera.getOffX() + gc.getWidth()), gm.camera.getOffY(), gm.particles.AUTOMATIC);
        }
        bgParticle++;
    }
}
