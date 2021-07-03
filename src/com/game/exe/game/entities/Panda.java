package com.game.exe.game.entities;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;
import com.game.exe.engine.gfx.Image;

import java.io.Serializable;

public class Panda extends GameObject implements Serializable {

    private Image pandaImage = new Image("/assets/panda/right.png");

    private int moveSpeed = 25;

    private float fallDistance = 0;
    private int fallSpeed = 10;

    private boolean grounded = false;

    private int originalPosX = 0;
    private boolean allowMove = true;
    private int amountToMove = 0;
    private int jumpCount = 0;

    private String type;

    public Panda(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.tileX = (int)posX;
        this.tileY = (int)posY;
        this.setOffX(0);
        this.setOffY(0);

        int speed = 100;
        this.physicsInit(this, speed);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {

        this.physicsApply(this,gm, dt);

        //Panda AI
        if(allowMove) { originalPosX = this.tileX; amountToMove = gm.random.generate(0,6) - 3; allowMove = false; }
        if(this.tileX < originalPosX + amountToMove && amountToMove > 0) {
            if(!gm.getCollision(this.tileX + 1,this.tileY)) {
                this.offX += dt * moveSpeed;
            }else{
                if(grounded) {
                    jump();
                    jumpCount++;
                }else if(jumpCount >= 2){ allowMove = true; amountToMove = gm.random.generate(0,7) - 3; jumpCount = 0; originalPosX = 0; }
            }
            if(pandaImage.getPath() == "/assets/panda/left.png") { setImage("right"); }
        }else if(this.tileX > originalPosX + amountToMove && amountToMove < 0){
            if(!gm.getCollision(this.tileX - 1,this.tileY)) {
                this.offX -= dt * moveSpeed;
            }else{
                if(grounded) {
                    jump();
                    jumpCount++;
                }else if(jumpCount >= 2){ allowMove = true; amountToMove = gm.random.generate(0,7) - 3; jumpCount = 0; originalPosX = 0; }
            }
            if(pandaImage.getPath() == "/assets/panda/right.png") { setImage("left"); }
        }else{ allowMove = true; amountToMove = gm.random.generate(0,6) - 4; originalPosX = 0;}

    }

    private void jump() {
        fallDistance = -3;
        grounded = false;
    }

    private void setImage(String type) {
        if(this.type == type) {
            return;
        }
        this.type = type;
        if(type == "right") {
            pandaImage = new Image("/assets/panda/right.png");
            return;
        }
        if(type == "left") {
            pandaImage = new Image("/assets/panda/left.png");
        }

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(pandaImage, (int) (posX), (int) (posY));
    }
}
