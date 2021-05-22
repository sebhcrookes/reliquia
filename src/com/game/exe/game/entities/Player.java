package com.game.exe.game.entities;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;
import com.game.exe.game.Sound;
import com.game.exe.engine.gfx.Image;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Player extends GameObject {

    public final float SPEED = 100;
    public float currentSpeed = 100;

    public boolean isSubmerged = false;
    private boolean isMoving = false;
    private boolean isMovingR = false;
    private boolean isMovingL = false;

    public Sound sound = new Sound();

    public String colour = "green";
    public int currentSprite = 0;

    public String playerPath = "/assets/player/" + colour + "/";
    public Image playerImage = new Image(playerPath + currentSprite + ".png");
    public String facing = "right";

    public int dashTime = 0;
    private boolean isDash = false;
    private final float DASH = 0.3f;
    private float dashCooldown;

    private int mouseTileX = 0, mouseTileY = 0;

    public Player(float posX, float posY) {
        this.tag = "player";
        this.tileX = (int)posX;
        this.tileY = (int)posY;
        this.posX = (int)(posX * GameManager.TS);
        this.posY = (int)(posY * GameManager.TS);
        this.isItem = false;
        this.dashCooldown = this.DASH;

        int speed = 100;
        this.init(this, speed);
    }

    public Player(float posX, float posY, float offX, float offY) {
        this.tag = "player";
        this.tileX = (int)posX;
        this.tileY = (int)posY;
        this.posX = (int)(posX * GameManager.TS);
        this.posY = (int)(posY * GameManager.TS);
        this.offX = offX;
        this.offY = offY;
        this.isItem = false;
        this.dashCooldown = this.DASH;

        int speed = 100;
        this.init(this, speed);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {

        this.apply(this, gm, dt);
        if(isMovingR || isMovingL) { isMoving = true; } else { isMoving = false; }

        //region Item Pickup
        for(int i = 0; i < gm.objects.size(); i++) {
            if (gm.objects.get(i).isItem) {
                float objectX = gm.objects.get(i).getPosX();
                float objectY = gm.objects.get(i).getPosY();
                if (posX >= objectX - 16 && posX <= objectX + 16) {
                    if (posY >= objectY - 16 && posY <= objectY + 16) {
                        if (gm.inventory.canStore(gm.objects.get(i).tag)) {
                            if (gm.objects.get(i).customEntityData.getValue("PickupDelay") == null) {
                                gm.objects.get(i).setDead(true);
                                gm.inventory.pickup(gm.objects.get(i).tag);
                            }
                        }
                    }
                }
            }
        }
        //endregion

        //Movement
        //region Left Movement
        if(gc.getInput().isKey(gm.controls.left) && gm.controls.allowControls) {
            facing = "left";
            isMovingL = true;
            currentSprite = 1;
            if(gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1, tileY + (int)Math.signum(this.offY))) {
                if(this.offX > 0) {
                    this.offX -= dt * currentSpeed;
                    if(this.offX < 0) {
                        this.offX = 0;
                    }
                }else{
                    this.offX = 0;
                }
            }else{
                this.offX -= dt * currentSpeed;
            }
        }else{
            isMovingL = false;
        }
        //endregion
        //region Right Movement
        if(gc.getInput().isKey(gm.controls.right) && gm.controls.allowControls) {
            facing = "right";
            isMovingR = true;
            currentSprite = 0;
            if(gm.getCollision(tileX + 1, tileY) || gm.getCollision(tileX + 1, tileY + (int)Math.signum(this.offY))) {
                if(this.offX < 0) {
                    this.offX += dt * currentSpeed;
                    if(this.offX > 0) {
                        this.offX = 0;
                    }
                }else{
                    this.offX = 0;
                }
            }else{
                this.offX += dt * currentSpeed;
            }
        }else{
            isMovingR = false;
        }
        //endregion
        //region Dash Movement
        if(gc.getInput().isKey(KeyEvent.VK_SHIFT) && gm.controls.allowControls) {
            if(!isDash && dashCooldown == DASH) {
                dashTime = 10;
                currentSpeed += SPEED * 2;
                isDash = true;
            }
        }

        if(isDash || dashCooldown != DASH) {
            if(dashCooldown <= 0.0f)
                dashCooldown = DASH;
            else
                dashCooldown -= 0.01f;
        }

        if(dashTime > 0) {
            dashTime--;
            if (dashTime % 4 == 0) {
                gm.pm.createParticle("player", posX, posY);
            }
        }
        if(dashTime == 0) {
            if(isDash) {
                dashTime = -1;
                currentSpeed -= SPEED * 2;
                isDash = false;
            }
        }
        //endregion
        //region Jump
        if(gc.getInput().isKey(gm.controls.jump) || gc.getInput().isKey(gm.controls.alternateJump)) {
            if(this.grounded && gm.controls.allowControls) {
                if(!isSubmerged) {
                    sound.jumpSound.play();
                }
                gm.pm.createParticle("dust", posX + (playerImage.getW() >> 1), posY + playerImage.getH() - 3);
                this.fallDistance = -this.getJumpPower();
                this.grounded = false;
            }
        }
        //endregion

        //Shooting
        if(gc.getInput().isButtonDown(gm.controls.secondaryClick) && gm.inventory.itemSelected.equals("acidbottle")) {
            if(gm.inventory.hasItem("acidbottle")) {
                gm.addObject(new Acid(posX + 8, posY, currentSprite));
                gm.inventory.removeItem("acidbottle", 1);

            }else{
                //TODO: Play Sound for running out of bottles
            }
        }


        //Animation
        if(this.facing.equals("left")) { currentSprite = 1; }
        else if(this.facing.equals("right")) { currentSprite = 0; }

        if(!this.grounded) {
            if(currentSprite == 0) { currentSprite = 2; }
            if(currentSprite == 1) { currentSprite = 3; }
        } else {
            if(currentSprite == 2) { currentSprite = 0; }
            if(currentSprite == 3) { currentSprite = 1; }
        }

        mouseTileX = (int)(gc.getInput().getMouseX()+ gm.camera.getOffX());
        mouseTileY = (int)(gc.getInput().getMouseY()+ gm.camera.getOffY());

        updateImage();
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(playerImage, (int) this.posX, (int) this.posY);
        r.drawLine((int)(mouseTileX),(int)(mouseTileY),(int)this.posX,(int)this.posY,0xffffffff);
    }

    public void setLocation(int posX, int posY) {
        this.tileX = (int)posX;
        this.tileY = (int)posY;
        this.posX = posX * GameManager.TS;
        this.posY = posY * GameManager.TS;
    }

    public void updateImage() {
        if(playerImage.getPath() == playerPath + currentSprite + ".png")
            return;
        else
            playerImage = new Image(playerPath + currentSprite + ".png");
    }

    public void setColour(String colour) {
        try {
            this.colour = colour;
            this.playerPath = "/assets/player/" + this.colour + "/";
            updateImage();
        } catch(Exception e) {
            this.colour = "green";
            this.playerPath = "/assets/player/" + this.colour + "/";
            updateImage();
        }
    }

    public String getDirection() {
        return facing;
    }

    public void setDirection(String facing) {
        this.facing = facing;
    }

    public static float round(double n, int round2DecimalPlace) {
        BigDecimal instance = new BigDecimal(Double.toString(n));
        instance = instance.setScale(round2DecimalPlace, RoundingMode.HALF_UP);
        return instance.floatValue();
    }

    public String getColour() { return this.colour; }
}
