package com.game.exe.game.entities;

import com.game.exe.game.GameManager;

public class Physics {

    protected final float JUMP = 4;
    protected int SPEED = 100;

    protected int fallSpeed = 10;
    protected float fallDistance = 0;
    private float jumpPower = 4;
    protected boolean grounded = false;
    private boolean underwater = false;
    protected float offY;
    protected float offX;

    protected final float MULTIPLIER = (float)(1.0 / 60.0);

    public Physics() {}

    public void init(int speed) {
        this.SPEED = speed;
    }

    public void apply(GameObject obj, GameManager gm, float dt) {
        fallDistance += MULTIPLIER * fallSpeed;

        this.offY += fallDistance;
        if(obj.tag != "acid") { //We don't want to run a grounded check on the acid bottle
            if (fallDistance > 0) {
                if ((gm.getCollision(obj.tileX, obj.tileY + 1) || gm.getCollision(obj.tileX + (int) Math.signum((int) offX), obj.tileY + 1)) && offY >= 0) {
                    fallDistance = 0;
                    offY = 0;
                    grounded = true;
                }
            } else if (fallDistance < 0) {
                if ((gm.getCollision(obj.tileX, obj.tileY - 1) || gm.getCollision(obj.tileX + (int) Math.signum((int) offX), obj.tileY - 1)) && offY <= 0) {
                    fallDistance = 0;
                    offY = 0;
                }
            }
        }

        //Water Physics
        if(isUnderwater()) {
            grounded = true;
            fallSpeed = 0;
        }

        //Final Position Calculation
        //Y
        if(offY > gm.TS / 2) {
            obj.tileY++;
            offY -= gm.TS;
        }else if(offY < -gm.TS / 2) {
            obj.tileY--;
            offY += gm.TS;
        }

        //X
        if(offX > gm.TS / 2) {
            offX -= gm.TS;
            obj.tileX++;
        }else if(offX < -gm.TS / 2) {
            offX += gm.TS;
            obj.tileX--;
        }

        obj.posX = obj.tileX * gm.TS + this.offX;
        obj.posY = obj.tileY * gm.TS + this.offY;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public float getOffX() {
        return offX;
    }

    public void setOffX(float offX) {
        this.offX = offX;
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

    public int getSPEED() {
        return SPEED;
    }
}
