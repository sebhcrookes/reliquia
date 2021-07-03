package com.game.exe.game.entities;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.position.Vector2;
import com.game.exe.game.GameState;
import com.game.exe.engine.gfx.Image;

import java.io.Serializable;

import static com.game.exe.game.GameState.TS;

public class Acid extends GameObject implements Serializable {

    private int direction;

    private float fallDistance = -2;
    private int fallSpeed = 10;

    private Image acidBottle = new Image("/assets/items/acidBottle.png");
    private Physics physics = new Physics();

    public Acid(float posX, float posY, int direction) {
        this.tag = "acid";
        this.direction = direction;
        this.position = new Vector2((int)posX, (int)posY);
        this.tileX = (int) posX / TS + 1;
        this.tileY = (int) posY / TS + 1;
        physics.offX = (int) posX % TS - (TS);
        physics.offY = (int) posY % TS - (TS);

        int speed = 300;
        physics.physicsInit(this, speed);
    }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {
        switch(direction)
        {
            case 0: physics.offX += physics.getSpeed() * dt; physics.offY -= (physics.getSpeed() * dt) / 2; break;
            case 1: physics.offX -= physics.getSpeed() * dt; physics.offY -= (physics.getSpeed() * dt) / 2; break;
        }

        physics.physicsApply(this, gm, dt);

        if(gm.getCollision(tileX,tileY)) {
            this.dead = true;
            gm.player.sound.explodeSound.play();
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) { r.drawImage(acidBottle, (int)position.getPosX(), (int)position.getPosY()); }
}
