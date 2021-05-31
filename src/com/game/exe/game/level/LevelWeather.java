package com.game.exe.game.level;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameManager;
import com.game.exe.game.particles.ParticleManager;

public class LevelWeather {

    private GameManager gm;

    public static final int CLEAR = 0;
    public static final int RAIN = 1;
    public static final int SNOW = -1;

    private int weatherIntensity = 0;
    private String weatherType = "clear";
    private int bgParticle = 0;

    public LevelWeather(GameContainer gc, GameManager gm, LevelManager lm) {
        this.gm = gm;
    }

    public void set(int weatherType) {
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
        }
    }

    public void update(GameContainer gc, GameManager gm, float dt) {
        if(bgParticle >= weatherIntensity && weatherIntensity != 0) {
            bgParticle = 0;

            gm.pm.createParticle(weatherType,gm.random.generate((int)gm.camera.getOffX(), (int)gm.camera.getOffX() + gc.getWidth()), gm.camera.getOffY(), ParticleManager.WIND_STRONG);
        }
        bgParticle++;
    }
}
