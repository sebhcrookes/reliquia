package com.game.exe.game.entities;

import com.game.exe.game.GameState;

public class Physics {

    protected final float JUMP = 4;
    protected final int SPEED = 100;
    protected int speed = 100;

    protected int fallSpeed = 10;
    protected float fallDistance = 0;
    private float jumpPower = 4;
    protected boolean grounded = false;
    private boolean underwater = false;
    protected float offY = 0;
    protected float offX = 0;

    protected float velX = 0;
    protected float velY = 0;

    private GameObject obj;

    private boolean isInitialised = false;

    public Physics() {}

    public void physicsInit(GameObject obj, int speed) {
        this.speed = speed;
        this.obj = obj;
        this.isInitialised = true;
    }

    public void physicsApply(GameObject obj, GameState gm, float dt) {
        if(!isInitialised) {
            return;
        }

        float MULTIPLIER = (float) (1.0 / 60.0);
        fallDistance += MULTIPLIER * fallSpeed; // Calculate fallDistance

        this.offY += fallDistance;
        if(obj.tag != "acid") { //We don't want to run a grounded check on the acid bottle
            if (fallDistance > 0) {
                if ((gm.getCollision(obj.tileX, obj.tileY + 1) || gm.getCollision(obj.tileX + (int) Math.signum((int) this.offX), obj.tileY + 1)) && offY >= 0) {
                    fallDistance = 0;
                    offY = 0;
                    grounded = true;
                }
            } else if (fallDistance < 0) {
                if ((gm.getCollision(obj.tileX, obj.tileY - 1) || gm.getCollision(obj.tileX + (int) Math.signum((int) this.offX), obj.tileY - 1)) && offY <= 0) {
                    fallDistance = 0;
                    offY = 0;
                }
            }
        }

        //TODO: Water Physics

        // Final Position Calculation: Y
        if(offY > GameState.TS >> 1) {
            obj.tileY++;
            offY -= GameState.TS;
        }else if(offY < -GameState.TS >> 1) {
            obj.tileY--;
            offY += GameState.TS;
        }

        // Final Position Calculation: X
        if(this.offX > GameState.TS >> 1) {
            this.offX -= GameState.TS;
            obj.tileX++;
        }else if(this.offX < -GameState.TS >> 1) {
            this.offX += GameState.TS;
            obj.tileX--;
        }

        // Apply the changes
        obj.getPosition().setPosX((int)(obj.tileX * GameState.TS + this.offX));
        obj.getPosition().setPosY((int)(obj.tileY * GameState.TS + this.offY));
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isUnderwater() {
        return underwater;
    }

    public void setUnderwater(boolean underwater) {
        this.underwater = underwater;
    }

    public float getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(float jumpPower) {
        this.jumpPower = jumpPower;
    }

    public float getJUMP() {
        return JUMP;
    }

    public int getSpeed() {
        return speed;
    }
}
