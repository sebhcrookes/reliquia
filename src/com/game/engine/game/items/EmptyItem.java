package com.game.engine.game.items;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;
import com.game.engine.game.entities.components.AABBComponent;

public class EmptyItem extends Item {
    private final float FALL_SPEED = 10F;

    private float fallSpeed = FALL_SPEED;

    public EmptyItem(int posX, int posY) {
        init(posX, posY);
    }

    public EmptyItem() {
        init(0, 0);
    }

    private void init(int posX, int posY) {
        this.namespace = "game-exe";
        this.identifier = "empty_item";
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

        this.updateComponents(api, gs, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawLine((int)posX, (int)posY, (int)posX + width, (int)posY + height, 0xFFFFFFFF);
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {
        collideWith("game-exe:tile", other);
        this.collideComponents(other);
    }
}
