package com.game.engine.game.blocks;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;
import com.game.engine.game.entities.Health;

import java.util.Random;

public abstract class ParticleEmitter extends GameObject {

    protected int baseFrequency;

    protected int frequency;

    protected int delay;

    public ParticleEmitter(int x, int y, double baseFrequency) {
        this.namespace = "game-exe";
        this.identifier = "particle_emitter";
        this.width = GameState.TS;
        this.height = GameState.TS;
        this.padding = 0;
        this.paddingTop = 0;
        this.posX = x * GameState.TS;
        this.posY = y * GameState.TS;

        this.delay = new Random().nextInt(15);

        this.baseFrequency = (int)baseFrequency * 10;
        this.frequency = (int)baseFrequency * 10;

        this.tileX = x;
        this.tileY = y;

        this.health = Health.INFINITE;
    }

    @Override
    public abstract void update(EngineAPI api, GameState gs, float dt);

    public void updateEmitter(EngineAPI api, GameState gs, float dt, GameObject particle) {

        if(this.delay != 0) {
            this.delay--;
            return;
        }

        if(frequency == 0) {
            try {
                gs.getObjectManager().addObject(particle);
                frequency = baseFrequency;
            } catch(Exception ignored) {}
        }

        frequency--;
    }

    @Override
    public abstract void render(EngineAPI api, Renderer r);

    @Override
    public abstract void collision(GameObject other);
}