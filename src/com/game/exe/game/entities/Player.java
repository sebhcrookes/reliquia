package com.game.exe.game.entities;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.engine.position.Vector2;
import com.game.exe.game.GameState;
import com.game.exe.game.Sound;
import com.game.exe.game.ui.UIManager;
import com.game.exe.game.ui.UIObject;

import java.awt.event.KeyEvent;

public class Player extends GameObject {

    private final float DASH = 0.3f;

    public Health health = new Health();

    public float currentSpeed = 100;
    public boolean isSubmerged = false;
    public Sound sound = new Sound();
    public String colour = "green";
    public int currentSprite = 0;
    public String playerPath = "/assets/player/" + colour + "/";
    public Image playerImage = new Image(playerPath + currentSprite + ".png");
    public String facing = "right";
    public int dashTime = 0;

    private boolean isMoving = false;
    private boolean isMovingR = false;
    private boolean isMovingL = false;
    private boolean isDash = false;
    private float dashCooldown;

    public Player(float posX, float posY) {
        this.tag = "player";
        this.tileX = (int) posX;
        this.tileY = (int) posY;
        this.position = new Vector2((int)(posX * GameState.TS), (int)(posY * GameState.TS));
        this.isItem = false;
        this.dashCooldown = this.DASH;

        init();
    }

    public Player(float posX, float posY, float offX, float offY) {
        this.tag = "player";
        this.tileX = (int) posX;
        this.tileY = (int) posY;
        this.position = new Vector2((int)(posX * GameState.TS), (int)(posY * GameState.TS));
        this.offX = offX;
        this.offY = offY;
        this.isItem = false;
        this.dashCooldown = this.DASH;

        init();
    }

    private void init() {
        int speed = 100;
        int health = 4;
        this.physicsInit(this, speed);
        this.health.init(this, health);

        UIManager.addUIObject(new PlayerHealthUI());
    }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {

        this.physicsApply(this, gm, dt);
        //this.health.update(gc, gm, dt);
        isMoving = isMovingR || isMovingL;

        //region Item Pickup
        for (int i = 0; i < gm.objects.size(); i++) {
            if (gm.objects.get(i).isItem) {
                float objectX = gm.objects.get(i).getPosition().getPosX();
                float objectY = gm.objects.get(i).getPosition().getPosY();
                if (position.getPosX() >= objectX - 16 && position.getPosX() <= objectX + 16) {
                    if (position.getPosY() >= objectY - 16 && position.getPosY() <= objectY + 16) {
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
        if (gc.getInput().isKey(gm.controls.left) && gm.controls.allowControls) {
            facing = "left";
            isMovingL = true;
            currentSprite = 1;
            if (gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1, tileY + (int) Math.signum(this.offY))) {
                if (this.offX > 0) {
                    this.offX -= dt * currentSpeed;
                    if (this.offX < 0) {
                        this.offX = 0;
                    }
                } else {
                    this.offX = 0;
                }
            } else {
                this.offX -= dt * currentSpeed;
            }
        } else {
            isMovingL = false;
        }
        //endregion
        //region Right Movement
        if (gc.getInput().isKey(gm.controls.right) && gm.controls.allowControls) {
            facing = "right";
            isMovingR = true;
            currentSprite = 0;
            if (gm.getCollision(tileX + 1, tileY) || gm.getCollision(tileX + 1, tileY + (int) Math.signum(this.offY))) {
                if (this.offX < 0) {
                    this.offX += dt * currentSpeed;
                    if (this.offX > 0) {
                        this.offX = 0;
                    }
                } else {
                    this.offX = 0;
                }
            } else {
                this.offX += dt * currentSpeed;
            }
        } else {
            isMovingR = false;
        }
        //endregion
        //region Dash Movement
        if (gc.getInput().isKey(KeyEvent.VK_SHIFT) && gm.controls.allowControls) {
            if (!isDash && dashCooldown == DASH) {
                dashTime = 10;
                currentSpeed += this.speed * 2;
                isDash = true;
            }
        }

        if (isDash || dashCooldown != DASH) {
            if (dashCooldown <= 0.0f)
                dashCooldown = DASH;
            else
                dashCooldown -= 0.01f;
        }

        if (dashTime > 0) {
            dashTime--;
            if (dashTime % 4 == 0) {
                gm.pm.createParticle("player", position.getPosX(), position.getPosY());
            }
        }
        if (dashTime == 0) {
            if (isDash) {
                dashTime = -1;
                currentSpeed -= this.speed * 2;
                isDash = false;
            }
        }
        //endregion
        //region Jump
        if (gc.getInput().isKey(gm.controls.jump) || gc.getInput().isKey(gm.controls.alternateJump)) {
            if (this.grounded && gm.controls.allowControls) {
                if (!isSubmerged) {
                    sound.jumpSound.play();
                }
                gm.pm.createParticle("dust", position.getPosX() + (playerImage.getWidth() >> 1), position.getPosY() + playerImage.getHeight() - 3);
                this.fallDistance = -this.getJumpPower();
                this.grounded = false;
            }
        }
        //endregion

        //Shooting
        if (gc.getInput().isButtonDown(gm.controls.secondaryClick) && gm.inventory.itemSelected.equals("acidbottle")) {
            if (gm.inventory.hasItem("acidbottle")) {
                gm.addObject(new Acid(position.getPosX() + 8, position.getPosY(), currentSprite));
                gm.inventory.removeItem("acidbottle", 1);

            } else {
                //TODO: Play Sound for running out of bottles
            }
        }


        //Animation
        if (this.facing.equals("left")) {
            currentSprite = 1;
        } else if (this.facing.equals("right")) {
            currentSprite = 0;
        }

        if (!this.grounded) {
            if (currentSprite == 0) {
                currentSprite = 2;
            }
            if (currentSprite == 1) {
                currentSprite = 3;
            }
        } else {
            if (currentSprite == 2) {
                currentSprite = 0;
            }
            if (currentSprite == 3) {
                currentSprite = 1;
            }
        }

        updateImage();
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(playerImage, (int) this.position.getPosX(), (int) this.position.getPosY());
        //r.drawLine((int)(mouseTileX),(int)(mouseTileY),(int)this.posX,(int)this.posY,0xffffffff);
    }

    public void setLocation(int posX, int posY) {
        this.tileX = posX;
        this.tileY = posY;
        this.position.setPosX(posX * GameState.TS);
        this.position.setPosY(posY * GameState.TS);
    }

    public void updateImage() {
        if (!playerImage.getPath().equals(playerPath + currentSprite + ".png"))
            playerImage = new Image(playerPath + currentSprite + ".png");
    }

    public String getDirection() {
        return facing;
    }

    public void setDirection(String facing) {
        this.facing = facing;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String colour) {
        try {
            this.colour = colour;
            this.playerPath = "/assets/player/" + this.colour + "/";
            updateImage();
        } catch (Exception e) {
            this.colour = "green";
            this.playerPath = "/assets/player/" + this.colour + "/";
            updateImage();
        }
    }
}

/**
 * game.exe: Player Health UI class
 * Contains code for health bar
 */
class PlayerHealthUI extends UIObject {
    private int health;
    private Image healthImage = new Image("/assets/ui/health/10.png");
    private boolean initialised = false;

    public PlayerHealthUI() { this.setTag("player_health"); }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {
        this.health = gm.player.health.getHealth();
        if(!initialised) {
            healthUpdate();
            initialised = true;
        }

        this.setPosX((gc.getWidth() - healthImage.getWidth()) >> 1);
        this.setPosY((gc.getHeight() - healthImage.getHeight() + 5) - 6 + gm.cinematicCount);
    }

    @Override
    public void render(GameContainer gc, GameState gm, Renderer r) {
        r.drawImage(healthImage, (gc.getWidth() - healthImage.getWidth()) / 2, (gc.getHeight() - healthImage.getHeight() + 5) - 6 + gm.cinematicCount);
    }

    public void healthUpdate() {
        try {
            healthImage = new Image("/assets/ui/health/" + this.health + ".png");
        }catch(Exception ignored) {
            ignored.printStackTrace();
            healthImage = new Image("/assets/ui/health/0.png");
        }
    }
}
