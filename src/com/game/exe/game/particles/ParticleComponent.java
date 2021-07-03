package com.game.exe.game.particles;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameManager;

public abstract class ParticleComponent {

    public abstract void update(Particle p, GameContainer gc, GameManager gm, float dt);

}
