package com.game.reliquia.game.blocks;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.entities.GameObject;
import com.game.reliquia.game.entities.Health;
import com.game.reliquia.game.entities.components.AABBComponent;

public class Tile extends GameObject {

    public Tile(int x, int y) {
        this.namespace = "reliquia";
        this.identifier = "tile";
        this.width = GameState.TS;
        this.height = GameState.TS;
        this.padding = 0;
        this.paddingTop = 0;
        this.posX = x * GameState.TS;
        this.posY = y * GameState.TS;

        this.tileX = x;
        this.tileY = y;

        this.health = Health.INFINITE;

        AABBComponent aabb = new AABBComponent(this);

        this.addComponent(aabb);

        this.showDebug = false;
    }

//    float temp = 0;

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {

//        temp += dt;
//        posY += Math.cos(temp);

        this.updateComponents(api, gs, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {}
}