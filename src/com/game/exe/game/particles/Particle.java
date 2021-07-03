package com.game.exe.game.particles;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameState;
import com.game.exe.game.entities.GameObject;
import com.game.exe.engine.gfx.Image;

import java.awt.*;

public class Particle extends GameObject {

    public float posX;
    public float posY;

    private ParticleComponent[] component;

    private Image image;
    private int existenceTime;
    private int decayType;

    public Particle(float posX, float posY, String imagePath, int existenceTime, int decayType, int fallSpeed, ParticleComponent[] component) {
        this.posX = posX;
        this.posY = posY;
        this.image = new Image(imagePath);
        this.existenceTime = existenceTime;
        this.decayType = decayType;
        this.fallSpeed = fallSpeed;
        this.component = component;
    }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {
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
                    for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {
                        int pixel = image.getP(i);
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

        for(int i = 0; i < component.length; i++) {
            component[i].update(this, gc, gm, dt);
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(this.image, (int)this.posX, (int)this.posY);
    }
}
