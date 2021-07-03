package com.game.exe.game.entities.items;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.position.Vector2;
import com.game.exe.game.GameState;
import com.game.exe.game.entities.GameObject;
import com.game.exe.engine.gfx.Image;

import java.io.Serializable;

import static com.game.exe.game.GameState.TS;

public class Item extends GameObject implements Serializable {

    public String itemID;
    private Image itemImage;

    private int tileX, tileY;
    private float offX, offY;

    private float fallDistance;
    private int fallSpeed = 10;

    private float velX;

    private boolean grounded = false;
    private int groundedAnimCount = 0;

    private String fontPlaceholder;
    private int tempOffX;

    public Item(String itemID, float posX, float posY, Image sprite, boolean isItem, String fontPlaceholder, float velX, float velY) {
        this.tag = itemID;
        this.itemID = itemID;
        this.position = new Vector2((int)posX, (int)posY);
        this.itemImage = sprite;
        this.tileX = (int)posX;
        this.tileY = (int)posY;
        this.offX = 0;
        this.offY = 0;
        this.isItem = isItem;
        this.fontPlaceholder = fontPlaceholder;
        this.fallDistance = velY;
        this.velX = velX;

        this.customEntityData.setValue("PickupDelay", 20);
    }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {

        tempOffX = (int) offX;
        int tempTileX = tileX;

        try {
            String stringVal = String.valueOf(customEntityData.getValue("PickupDelay"));
            int intVal = Integer.parseInt(stringVal);

            if (intVal <= 0) {
                customEntityData.removeValue("PickupDelay"); // Removing PickupDelay
            } else {
                this.customEntityData.setValue("PickupDelay", intVal - 1); // Subtracting from PickupDelay
            }
        }catch(Exception ignored) {}

        if(velX != 0) {
            if(velX < 0) {
                velX += 0.1;
            }else if(velX > 0) {
                velX -= 0.1;
            }
            offX += velX;
            if (gm.getCollision(tileX, tileY)) {
                velX = 0;
            } else if (gm.getCollision(tileX, tileY)) {
                velX = 0;
            }
            if(gm.distanceBetween(velX, 0) < 1 && gm.distanceBetween(velX, 0) > 0) {
                velX = 0;
            }
        }

        if(gm.getCollision((int) position.getPosX() / gm.TS, (int) position.getPosY() / 16 - 1)) { grounded = true; }
        else { grounded = false; }

        fallDistance += dt * fallSpeed;
        offY += fallDistance;

        if(fallDistance > 0) {
            if ((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tempTileX + (int) Math.signum((int) tempOffX), tileY + 1)) && offY >= 0) {
                fallDistance = 0;
                offY = 0;
                grounded = true;
            }
        }
        if(fallDistance < 0) {
            if ((gm.getCollision(tileX, tileY - 1) || gm.getCollision(tempTileX + (int) Math.signum((int) tempOffX), tileY - 1)) && offY <= 0) {
                fallDistance = 0;
                offY = 0;
                grounded = false;
            }
        }

        if(grounded) {
            groundedAnimCount++;
            if(groundedAnimCount < 60 && groundedAnimCount >= 0)
                offY = TS - (2 + itemImage.getHeight()) + 1;
            if(groundedAnimCount <= 119 && groundedAnimCount >= 60)
                offY = TS - (2 + itemImage.getHeight());
            if(groundedAnimCount == 119)
                groundedAnimCount = 0;
        }
        //End of jump and gravity

        //Beginning of Final Position Calculation
        //Y
        if(offY > TS / 2) {
            tileY++;
            offY -= TS;
        }
        if(offY < -TS / 2) {
            tileY--;
            offY += TS;
        }

        //X
        if(offX > TS / 2) {
            tileX++;
            offX -= TS;
        }
        if(offX < -TS / 2) {
            tileX--;
            offX += TS;
        }

        position.setPosX((int)(tileX * TS + offX));
        position.setPosY((int)(tileY * TS + offY));
        //End of Final Position Calculation
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(itemImage, (int) (position.getPosX()), (int) (position.getPosY()));
    }
}