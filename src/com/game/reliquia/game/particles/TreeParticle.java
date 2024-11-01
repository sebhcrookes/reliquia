package com.game.reliquia.game.particles;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.gfx.Image;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.GlobalRandom;
import com.game.reliquia.game.weather.Wind;
import com.game.reliquia.game.entities.GameObject;
import com.game.reliquia.game.entities.components.AABBComponent;

public class TreeParticle extends GameObject {

    private Image image;

    private int alpha;

    public TreeParticle(float posX, float posY) {
        this.setNamespace("reliquia");
        this.setIdentifier("tree_particle");
        this.image = new Image("/particles/tree/" + GlobalRandom.nextInt(3) + ".png");
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.padding = 0;
        this.paddingTop = 0;
        this.posX = posX;
        this.posY = posY;
        this.tileX = (int)posX / GameState.TS;
        this.tileY = (int)posY / GameState.TS;
        this.offX = posX % GameState.TS;
        this.offY = posY % GameState.TS;

        AABBComponent aabb = new AABBComponent(this);

        this.addComponent(aabb);
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {

        this.offY += GlobalRandom.nextDouble() / 2 + Wind.power / 3;

        this.offX -= Wind.power;

        if(GlobalRandom.nextInt(20) == 1) {
            this.image.setRotation(image.getRotation() + GlobalRandom.nextInt(20) - 10);
        }

        if(GlobalRandom.nextInt(40) == 1) {
            this.offX++;
        } else if(GlobalRandom.nextInt(40) == 1) {
            this.offX--;
        }

        this.finalPositionCalculation();
        this.updateComponents(api, gs, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawImage(this.image, (int)this.posX, (int)this.posY);
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {
        if(other.getNamespacedID().equals("reliquia:tile") && !this.isDead()) {
            this.setDead(true);
        }
        this.collideComponents(other);
    }
}
