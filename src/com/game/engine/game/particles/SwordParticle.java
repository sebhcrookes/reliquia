package com.game.engine.game.particles;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.gfx.Image;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;

public class SwordParticle extends GameObject {

    private Image[] images = new Image[11];

    private int imageOn = 0;

    private int imageCounter = 1;

    public SwordParticle(float posX, float posY, int facing) {
        this.posX = posX;
        this.posY = posY;
        String facingTxt = facing == 0 ? "right" : "left";
        for(int i = 0; i < images.length; i++) {
            images[i] = new Image("/particles/sweep/" + facingTxt + "/" + i + ".png");
        }
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        if(imageCounter != 0) imageCounter--;
        else {
            imageCounter = 1;
            if(imageOn + 1 == 11) {
                this.setDead(true);
            } else imageOn++;
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawImage(this.images[imageOn], (int)this.posX, (int)this.posY);
    }

    @Override
    public void collision(GameObject other) {}
}
