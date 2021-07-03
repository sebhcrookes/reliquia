package com.game.exe.game.particles;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameState;
import com.game.exe.game.entities.GameObject;

public class ParticleManager {

    public static final ParticleComponent NONE = new ParticleComponent() {
        @Override
        public void update(Particle p, GameContainer gc, GameState gm, float dt) {}
    };

    public static final ParticleComponent COLLIDE = new ParticleComponent() {
        @Override
        public void update(Particle p, GameContainer gc, GameState gm, float dt) {
            if(gm.getParticleCollision((int)p.posX / GameState.TS, (int)p.posY / GameState.TS)) {
                p.setDead(true);
            }
        }
    };

    public static final int WIND_LIGHT = 1;
    public static final int WIND_MEDIUM = 2;
    public static final ParticleComponent WIND_STRONG = new ParticleComponent() {
        @Override
        public void update(Particle p, GameContainer gc, GameState gm, float dt) {
            p.posX -= 2;
        }
    };

    private GameObject[] particles;
    private GameState gm;


    public ParticleManager(GameState gm) {
        this.gm = gm;

        int particleLimit = 1000;
        this.particles = new GameObject[particleLimit];
    }

    public void update(GameContainer gc, GameState gm, float dt) {
        for(int i = 0; i < particles.length; i++) {
            if(particles[i] == null) { continue; }
            particles[i].update(gc, gm, dt);
            if(particles[i].isDead()) {
                particles[i] = null;
            }
        }
    }

    public void render(GameContainer gc, Renderer r) {
        for(int i = 0; i < particles.length; i++) {
            if(particles[i] == null) { continue; }
            particles[i].render(gc, r);
        }
    }

    public void createParticle(String type, float posX, float posY, ParticleComponent[] component) {
        internalParticle(type,posX,posY,component);
    }

    public void createParticle(String type, float posX, float posY) {
        internalParticle(type,posX,posY,new ParticleComponent[]{NONE});
    }

    private void internalParticle(String type, float posX, float posY, ParticleComponent[] component) {
        for(int i = 0; i < particles.length; i++) {
            if(particles[i] == null) {
                switch(type) {
                    case "dust":
                        particles[i] = new Particle(posX, posY, gm.sprite.dustImage.getPath(), 50, 2, 0, component);
                        break;
                    case "player":
                        particles[i] = new Particle(posX, posY, gm.player.playerImage.getPath(), 40, 2, 0, component);
                        break;
                    case "rain":
                        particles[i] = new Particle(posX, posY, gm.sprite.rainImage.getPath(), 100, 1,3, component);
                        break;
                    case "snow":
                        particles[i] = new Particle(posX, posY, gm.sprite.snowImage.getPath(), 100, 1,3, component);
                        break;
                }
                return;
            }
        }
    }
}
