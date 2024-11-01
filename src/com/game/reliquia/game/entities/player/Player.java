package com.game.reliquia.game.entities.player;

import com.game.reliquia.engine.audio.AudioClip;
import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.gfx.Image;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.entities.Location;
import com.game.reliquia.game.blocks.Blocks;
import com.game.reliquia.game.entities.GameObject;
import com.game.reliquia.game.entities.Projectile;
import com.game.reliquia.game.entities.components.AABBComponent;
import com.game.reliquia.game.entities.components.InventoryComponent;
import com.game.reliquia.game.particles.FootstepParticle;
import com.game.reliquia.game.particles.PlayerParticle;
import com.game.reliquia.game.particles.SwordParticle;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Player extends GameObject {

    private final float SPEED = 100F;
    private final float FALL_SPEED = 9.81F;
    private final float JUMP = 4F;

    private Image[] images = new Image[4];

    private float speed = SPEED;
    private float fallSpeed = FALL_SPEED;
    private float jump = JUMP;

    private Artifacts artifacts;

    private boolean previousGrounded;
    private double previousHealth;

    private Image healthImage;

    public Player(int posX, int posY) {
        this.namespace = "reliquia";
        this.identifier = "player";

        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        this.facing = 0;

        this.posX = posX * GameState.TS;
        this.posY = posY * GameState.TS;
        this.width = GameState.TS;
        this.height = GameState.TS;

        this.artifacts = new Artifacts(this);
        this.artifacts.loadAll();

        this.previousGrounded = grounded;

        this.healthImage = new Image("/objects/player/health/" + (int)health + ".png");

        // Padding initialisation
        padding = 0;
        paddingTop = 2;

        // Image initialisation
        String imagePath = "/objects/player/";

        for(int i = 0; i < images.length; i++) {
            images[i] = new Image(imagePath + i + ".png");
        }

        this.addComponent(new AABBComponent(this));

        InventoryComponent inventory = new InventoryComponent(this);

        this.addComponent(inventory);
    }


    private int dashSpeed = 0;
    private int dashTime = 0;

    private boolean inWater = false;

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        if(health == 0) {
            InventoryComponent inventory = ((InventoryComponent)(this.findComponent("inventory")));
            inventory.deathDrop(gs);
            AudioClip.getSound("entity_death").play();
            this.setDead(true);
        }

        // Water Physics
        if(Blocks.numberToString(gs.getLevelManager().getCollisionDetails(tileX, tileY)).equals("water")) {
            inWater = true;
            grounded = true;
            fallSpeed = 5F;
            speed = 50F;
            jump = 1F;
            if(Blocks.numberToString(gs.getLevelManager().getCollisionDetails(tileX, tileY - 1)).equals("air")) {
                jump = 1.6F;
                speed = 50F;
            }

        } else {
            inWater = false;
            speed = SPEED;
            fallSpeed = FALL_SPEED;
            jump = JUMP;
        }

        // Left and Right
        if (api.getInput().isKey(KeyEvent.VK_D)) {
            this.facing = Location.FACING_RIGHT;
            offX += dt * speed;
            if(grounded && new Random().nextInt(10) == 2){
                gs.getObjectManager().addObject(new FootstepParticle(posX + (this.width >> 1), posY + this.height, new Random().nextInt(2)));
            }
        }

        if (api.getInput().isKey(KeyEvent.VK_A)) {
            this.facing = Location.FACING_LEFT;
            offX -= dt * speed;
            if(grounded && new Random().nextInt(10) == 2) {
                gs.getObjectManager().addObject(new FootstepParticle(posX + (this.width >> 1), posY + this.height, new Random().nextInt(2)));
            }
        }

        // Land Particles
        if(previousGrounded != grounded && grounded) {
            gs.getObjectManager().addObject(new FootstepParticle(posX + (this.width >> 1), posY + this.height, 0));
        }

        // Dash
        if(api.getInput().isKeyDown(KeyEvent.VK_SHIFT)) {
            if(artifacts.isEffectorActive("dash")) {
                dashTime = 30;
                dashSpeed = (facing == Location.FACING_RIGHT ? 9 : -9);
            }
        }

        // Left click events
        if(api.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            if(((InventoryComponent)this.findComponent("inventory")).getHeldItem().getNamespacedID().equals("reliquia:bamboo_sword")) {

                int mX = (int)(api.getInput().getMouseX() + api.getRenderer().getCamX());
                int mY = (int)(api.getInput().getMouseY() + api.getRenderer().getCamY());

                gs.getObjectManager().getObjectAt(mX, mY).decrementHealth();
                int facing = 0;
                if(mX < posX) {
                    facing = 1;
                }
                gs.getObjectManager().addObject(new SwordParticle(mX, mY, facing));
            }
        }

        if(dashTime > 0) {
            if (dashSpeed < 0) {
                offX += dashSpeed;
            }else if(dashSpeed > 0) {
                offX += dashSpeed;
            }
            if(dashTime == 30 || dashTime == 27 || dashTime == 1) {
                gs.getObjectManager().addObject(new PlayerParticle(posX, posY, this.facing + (grounded ? 0 : 2)));
            }
            dashSpeed/=1.1;
            dashTime--;
            if(dashSpeed == 0 && dashTime != 0) {
                dashTime = 1;
            }
        }

        updatePhysics(api, gs, dt, fallSpeed);

        if (api.getInput().isKey(KeyEvent.VK_W) && grounded)
        {
            fallDistance = -jump;
            grounded = false;
            if(!inWater) AudioClip.getSound("player_jump").play();
        }

        if(api.getInput().isKeyDown(KeyEvent.VK_UP))
            gs.getObjectManager().addObject(new Projectile(tileX, tileY, offX + (width >> 1), offY + (height >> 1), Projectile.DIRECTION_UP));
        if(api.getInput().isKeyDown(KeyEvent.VK_RIGHT))
            gs.getObjectManager().addObject(new Projectile(tileX, tileY, offX + (width >> 1), offY + (height >> 1), Projectile.DIRECTION_RIGHT));
        if(api.getInput().isKeyDown(KeyEvent.VK_DOWN))
            gs.getObjectManager().addObject(new Projectile(tileX, tileY, offX + (width >> 1), offY + (height >> 1), Projectile.DIRECTION_DOWN));
        if(api.getInput().isKeyDown(KeyEvent.VK_LEFT))
            gs.getObjectManager().addObject(new Projectile(tileX, tileY, offX + (width >> 1), offY + (height >> 1),Projectile.DIRECTION_LEFT));

        finalPositionCalculation();

        this.updateComponents(api, gs, dt);

        if(gs.getLevelManager().getCollisionDetails(this.tileX, this.tileY) == 4) {
            gs.getLevelManager().loadLevel(gs.getLevelManager().getLevelNumber() + 1);
        }

        if(health != previousHealth || health == 0) {
            healthImage = new Image("/objects/player/health/" + (int)health + ".png");
        }

        previousGrounded = grounded;
        previousHealth = health;
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        if(!this.isDead()) {
            r.drawImage(images[this.facing + (grounded ? 0 : 2)], (int) posX, (int) posY);
        }else{
            r.drawFillRect(0, 0, (int)r.getCamX() + api.getWidth(), (int)r.getCamY() + api.getHeight(), 0x56CC3F4D);
            r.drawText("You died!",
                    (int)r.getCamX() + (api.getWidth() / 2) - (r.getFont().getTextLength("You died!") / 2),
                    (int)r.getCamY() + (api.getHeight() / 2) - (r.getFont().getFontHeight() / 2) - 10,
                    0xFFFFFFFF);
            r.drawText("Press enter",
                    (int)r.getCamX() + (api.getWidth() / 2) - (r.getFont().getTextLength("Press enter") / 2),
                    (int)r.getCamY() + (api.getHeight() / 2) - (r.getFont().getFontHeight() / 2),
                    0xFFFFFFFF);
        }

        int zDepth = r.getzDepth();

        // Health rendering
        r.drawImage(healthImage, (int)r.getCamX() + (api.getWidth() / 2) - (healthImage.getWidth() / 2), (int)r.getCamY() + api.getHeight() - healthImage.getHeight() - 1);
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {
        this.collideComponents(other);
        this.collideWith("reliquia:tile", other);
    }

    public Artifacts getArtifacts() {
        return artifacts;
    }
}
