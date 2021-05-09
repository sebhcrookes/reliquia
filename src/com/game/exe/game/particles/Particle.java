package com.game.exe.game.particles;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.util.Logger;
import com.game.exe.game.GameManager;
import com.game.exe.game.entities.GameObject;
import com.game.exe.engine.gfx.Image;

import java.awt.*;

public class Particle extends GameObject {

    private float posX;
    private float posY;

    private int component;

    private Image image;
    private int existenceTime;
    private int decayType;

    private int fallSpeed = 0;

    public Particle(float posX, float posY, String imagePath, int existenceTime, int decayType) {
        this.posX = posX;
        this.posY = posY;
        this.image = new Image(imagePath);
        this.existenceTime = existenceTime;
        this.decayType = decayType;
    }

    public Particle(float posX, float posY, String imagePath, int existenceTime, int decayType, int fallSpeed) {
        this.posX = posX;
        this.posY = posY;
        this.image = new Image(imagePath);
        this.existenceTime = existenceTime;
        this.decayType = decayType;
        this.fallSpeed = fallSpeed;
    }

    public Particle(float posX, float posY, String imagePath, int existenceTime, int decayType, int fallSpeed, int component) {
        this.posX = posX;
        this.posY = posY;
        this.image = new Image(imagePath);
        this.existenceTime = existenceTime;
        this.decayType = decayType;
        this.fallSpeed = fallSpeed;
        this.component = component;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        existenceTime--; //Subtract from existenceTime
        posY+=fallSpeed; //Fall

        switch(decayType) {
            case 1: //No Decay, removes from world when existenceTime is 0
                if(existenceTime <= 0) {
                    this.setDead(true);
                }
                break;
            case 2: //Fade Decay, alpha gets reduced to 0
                try {
                    for (int i = 0; i < image.getW() * image.getH(); i++) {
                        int pixel = image.getSingleP(i);
                        int alpha = ((pixel >> 24) & 0xff);

                        alpha -= alpha / existenceTime;
                        Color rgba = new Color((pixel >> 16) & 0xff, (pixel >> 8) & 0xff, (pixel) & 0xff, alpha);
                        image.setP(i, rgba.getRGB());
                    }
                }catch(Exception e) {}

                if(existenceTime <= 0) {
                    this.setDead(true);
                }
                break;
        }

        switch(component) {
            case Particles.WIND_LIGHT:
                if(gm.random.generate(1,4) == 2)
                    posX--;
                return;
            case Particles.WIND_MEDIUM:
                posX--;
                return;
            case Particles.WIND_STRONG:
                posX-=2;
                return;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(this.image, (int)this.posX, (int)this.posY);
    }
}
