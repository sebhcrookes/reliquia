package com.game.reliquia.game.weather;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.GlobalRandom;

public class Weather {

    private GameState gs;

    private int windChangeCooldown = 400;

    private boolean increasingWind = false;
    private boolean decreasingWind = false;

    private final double maxWindStrength = 1.3;

    public Weather(GameState gs) {
        this.gs = gs;
    }

    public void tick(EngineAPI api, GameState gs, float dt) {
        if(GlobalRandom.nextInt(150) == 1 && !decreasingWind && windChangeCooldown == 0) {
            increasingWind = true;
            windChangeCooldown = 200;
            if(Wind.power != maxWindStrength) {
                Wind.power = 0.1;
            }
        }
        if(GlobalRandom.nextInt(100) == 1 && windChangeCooldown == 0) { decreasingWind = true; windChangeCooldown = 400; }

        if(increasingWind) Wind.power *= 1.03;
        if(increasingWind && Wind.power >= maxWindStrength) { increasingWind = false; Wind.power = maxWindStrength; }
        if(decreasingWind) Wind.power /= 1.03;
        if(decreasingWind && Wind.power <= 0.01) { decreasingWind = false; Wind.power = 0; }

        if(increasingWind && decreasingWind) increasingWind = false;

        if(windChangeCooldown != 0) windChangeCooldown--;

//        Weather Debugging
//        System.out.println("Increasing: " + increasingWind);
//        System.out.println("Decreasing: " + decreasingWind);
//        System.out.println("Wind change: " + windChangeCooldown);
//        System.out.println("Power: " + Wind.power);
    }
}
