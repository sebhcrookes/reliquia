package com.game.engine.game.blocks;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;
import com.game.engine.game.particles.TreeParticle;

import java.util.Random;

public class TreeParticleEmitter extends ParticleEmitter {

    private GameObject currentParticle;

    public TreeParticleEmitter(int x, int y) {
        super(x, y, new Random().nextInt(8) + 20);
        currentParticle = new TreeParticle(posX, posY);
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        if(frequency == 0) {
            currentParticle = new TreeParticle(posX + new Random().nextInt(16), posY);
        }
        updateEmitter(api, gs, dt, currentParticle);

        this.updateComponents(api, gs, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {
        this.collideComponents(other);
    }
}
