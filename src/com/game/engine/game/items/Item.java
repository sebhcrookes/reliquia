package com.game.engine.game.items;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.gfx.Image;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;

public abstract class Item extends GameObject {

    protected Image itemImage;
    protected int pickupDelay = 0;

    @Override
    public abstract void update(EngineAPI api, GameState gs, float dt);

    @Override
    public abstract void render(EngineAPI api, Renderer r);

    @Override
    public abstract void collision(GameObject other);

    public Image getImage() {
        return itemImage;
    }

    public int getPickupDelay() {
        return pickupDelay;
    }

    public void setPickupDelay(int pickupDelay) {
        this.pickupDelay = pickupDelay;
    }
}
