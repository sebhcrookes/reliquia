package com.game.engine.game.particles;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.gfx.Image;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;

import java.awt.*;
import java.util.Random;

public class FootstepParticle extends GameObject {

    public float posX;
    public float posY;

    private Image image;
    private int existenceTime;

    public FootstepParticle(float posX, float posY, int randomNum) {
        this.namespace = "game-exe";
        this.identifier = "footstep_particle";

        this.fallDistance = 0;

        this.image = new Image("/particles/player/" + randomNum + ".png");

        this.velY = -3;
        this.velX = 0;

        this.padding = 0;
        this.paddingTop = 0;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();

        this.posX = posX;
        this.posY = posY - image.getHeight();

        existenceTime = 50;
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        existenceTime--; //Subtract from existenceTime

        if(new Random().nextInt(20) == 1) {
            posY -= 1;
        }

        try {
            for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {
                int pixel = image.getP(i);
                int alpha = ((pixel >> 24) & 0xff);

                alpha -= alpha / existenceTime;
                Color rgba = new Color((pixel >> 16) & 0xff, (pixel >> 8) & 0xff, (pixel) & 0xff, alpha);
                image.setP(i, rgba.getRGB());
            }
        } catch(Exception ignored) {}

        if(existenceTime <= 0) {
            this.setDead(true);
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawImage(this.image, (int)this.posX, (int)this.posY);
    }

    @Override
    public void collision(GameObject other) {
        this.collideWith("tile", other);
    }
}
