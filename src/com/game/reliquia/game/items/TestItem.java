package com.game.reliquia.game.items;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.gfx.Image;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.entities.GameObject;
import com.game.reliquia.game.entities.components.AABBComponent;

public class TestItem extends Item {
    private final float FALL_SPEED = 10F;

    private float fallSpeed = FALL_SPEED;

    public TestItem(int posX, int posY) {
        init(posX, posY);
    }

    public TestItem() {
        init(0, 0);
    }

    private void init(int posX, int posY) {
        this.namespace = "reliquia";
        this.identifier = "test_item";

        this.itemImage = new Image("/items/test.png");

        this.width = 10;
        this.height = 10;
        this.padding = 0;
        this.paddingTop = 0;
        this.posX = posX * GameState.TS;
        this.posY = posY * GameState.TS;
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        
        this.addComponent(new AABBComponent(this));
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        updatePhysics(api, gs, dt, fallSpeed);
        finalPositionCalculation();

        if(pickupDelay != 0) pickupDelay--;

        this.updateComponents(api, gs, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawImage(itemImage, (int)posX, (int)posY);
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {
        this.collideWith("reliquia:tile", other);
        this.collideComponents(other);
    }
}
