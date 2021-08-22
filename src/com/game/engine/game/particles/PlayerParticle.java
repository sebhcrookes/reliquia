package com.game.engine.game.particles;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.gfx.Image;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;

import java.awt.*;

public class PlayerParticle extends GameObject {

    public float posX;
    public float posY;

    private Image image;
    private int existenceTime;

    public PlayerParticle(float posX, float posY, int facing) {
        this.posX = posX;
        this.posY = posY;
        this.image = new Image("/particles/dash/" + facing + "/" + facing + ".png");
        existenceTime = 30;
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        existenceTime--; //Subtract from existenceTime

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
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawImage(this.image, (int)this.posX, (int)this.posY);
    }

    @Override
    public void collision(GameObject other) {}
}
