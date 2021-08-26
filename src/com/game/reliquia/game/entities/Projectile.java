package com.game.reliquia.game.entities;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.gfx.Image;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.entities.components.AABBComponent;

public class Projectile extends GameObject {

    public static final int DIRECTION_DOWN = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_UP = 2;
    public static final int DIRECTION_LEFT = 3;

    private Image image = new Image("/items/slimeball.png");

    protected int direction;
    protected float speed = 300F;

    public Projectile(int tileX, int tileY, float offX, float offY, int direction) {
        this.namespace = "reliquia";
        this.identifier = "projectile";

        this.fallDistance = 0;

        this.tileX = tileX;
        this.tileY = tileY;
        this.offX = offX;
        this.offY = offY;

        this.velY = -3;
        this.velX = 0;

        this.padding = 0;
        this.paddingTop = 0;
        this.width = 5;
        this.height = 5;

        this.addComponent(new AABBComponent(this));

        posX = (int)(tileX * GameState.TS + offX);
        posY = (int)(tileY * GameState.TS + offY);

        this.direction = direction;
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {

        updateGravity(api, gs, dt, 10F);
        offY += fallDistance;

        switch(direction) {
            case DIRECTION_DOWN: offY += speed * dt; break;
            case DIRECTION_RIGHT: offX += speed * dt; break;
            case DIRECTION_UP: offY -= speed * dt; break;
            case DIRECTION_LEFT: offX -= speed * dt; break;
        }

        if(offY > GameState.TS) {
            tileY++;
            offY -= GameState.TS;
        }
        if(offY < 0) {
            tileY--;
            offY += GameState.TS;
        }
        if(offX > GameState.TS) {
            tileX++;
            offX -= GameState.TS;
        }
        if(offX < 0) {
            tileX--;
            offX += GameState.TS;
        }

        posX = tileX * GameState.TS + offX;
        posY = tileY * GameState.TS + offY;

        this.updateComponents(api, gs, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawImage(image, (int)posX, (int)posY);
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {
        if (!other.getNamespacedID().equalsIgnoreCase("reliquia:player") && !other.getNamespacedID().equalsIgnoreCase("reliquia:projectile")) {
            if(!this.isDead()) {
                other.setHealth(other.getHealth() - 1);
                this.setDead(true);
            }
        }
    }
}
