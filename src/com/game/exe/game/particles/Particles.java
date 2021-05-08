package com.game.exe.game.particles;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;
import com.game.exe.game.entities.GameObject;

public class Particles {

    public final int PERMANENT = Integer.MAX_VALUE;
    public final int AUTOMATIC = 0;

    private GameObject[] particles = new GameObject[1000];
    private GameManager gm;


    public Particles(GameManager gm) {
        this.gm = gm;
    }

    public void update(GameContainer gc, GameManager gm, float dt) {
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

    public void createParticle(String type, float posX, float posY, int existenceTime) {
        for(int i = 0; i < particles.length; i++) {
            if(particles[i] == null) {
                switch(type) {
                    case "dust":
                        particles[i] = new Particle(posX, posY, gm.sprite.dustImage.getPath(), 50, 2);
                        break;
                    case "player":
                        particles[i] = new Particle(posX, posY, gm.player.playerImage.getPath(), 40, 2);
                        break;
                    case "rain":
                        particles[i] = new Particle(posX, posY, gm.sprite.rainImage.getPath(), 100, 1,3);
                        break;
                    case "snow":
                        particles[i] = new Particle(posX, posY, gm.sprite.snowImage.getPath(), 100, 1,3);
                        break;
                }
                return;
            }
        }
    }
}
