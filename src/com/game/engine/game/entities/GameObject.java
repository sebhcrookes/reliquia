package com.game.engine.game.entities;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.components.AABBComponent;
import com.game.engine.game.entities.components.Component;

import java.util.ArrayList;

public abstract class GameObject {

    protected int padding, paddingTop, paddingBottom;

    protected int tileX, tileY;
    protected float offX, offY;
    protected float velX, velY;

    protected boolean showDebug = true;

    protected String namespace;
    protected String identifier;

    protected float posX, posY;
    protected int width, height;
    protected int facing;
    protected boolean dead = false;

    protected String UUID;
    protected double health = Health.DEFAULT;

    protected boolean grounded = false;
    protected float fallDistance = 0;

    protected ArrayList<Component> components = new ArrayList<>();

    public abstract void update(EngineAPI api, GameState gs, float dt);
    public abstract void render(EngineAPI api, Renderer r);
    public abstract void collision(GameObject other);

    protected void updatePhysics(EngineAPI api, GameState gs, float dt, float fallSpeed) {
        updateGravity(api, gs, dt, fallSpeed);
        offY += fallDistance;

        offX += velX;
        offY += velY;

        velX /= 1.25;
        velY /= 1.25;
    }

    public void updateGravity(EngineAPI api, GameState gs, float dt, float fallSpeed) {
        fallDistance += dt * fallSpeed;
    }

    public void finalPositionCalculation() {
        if(offY > GameState.TS >> 1) {
            tileY++;
            offY -= GameState.TS;
        }
        if(offY < -GameState.TS >> 1) {
            tileY--;
            offY += GameState.TS;
        }
        if(offX > GameState.TS >> 1) {
            tileX++;
            offX -= GameState.TS;
        }
        if(offX < -GameState.TS >> 1) {
            tileX--;
            offX += GameState.TS;
        }

        posX = tileX * GameState.TS + offX;
        posY = tileY * GameState.TS + offY;

        if((int)fallDistance != 0) {
            grounded = false;
        }
    }

    public void collideWith(String namespaceID, GameObject other) {
        if(other.getNamespacedID().equalsIgnoreCase(namespaceID)) {
            AABBComponent thisC = (AABBComponent) this.findComponent("aabb");
            AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

            if(Math.abs(thisC.getLastCenterX() - otherC.getLastCenterX()) < thisC.getHalfWidth() + otherC.getHalfWidth()) {
                if (thisC.getCenterY() < otherC.getCenterY()) {
                    int distance = thisC.getHalfHeight() + otherC.getHalfHeight() - (otherC.getCenterY() - thisC.getCenterY());
                    offY -= distance;
                    posY -= distance;
                    thisC.setCenterY(thisC.getCenterY() - distance);
                    fallDistance = 0;
                    grounded = true;
                }
                if (thisC.getCenterY() > otherC.getCenterY()) {
                    int distance = thisC.getHalfHeight() + otherC.getHalfHeight() - (thisC.getCenterY() - otherC.getCenterY());
                    offY += distance;
                    posY += distance;
                    thisC.setCenterY(thisC.getCenterY() + distance);
                    fallDistance = 0;
                }
            }else{
                if (thisC.getCenterX() < otherC.getCenterX()) {
                    int distance = thisC.getHalfWidth() + otherC.getHalfWidth() - (otherC.getCenterX() - thisC.getCenterX());
                    offX -= distance;
                    posX -= distance;
                    thisC.setCenterX(thisC.getCenterX() - distance);
                }
                if (thisC.getCenterX() > otherC.getCenterX()) {
                    int distance = thisC.getHalfWidth() + otherC.getHalfWidth() - (thisC.getCenterX() - otherC.getCenterX());
                    offX += distance;
                    posX += distance;
                    thisC.setCenterX(thisC.getCenterX() + distance);
                }
            }
        }
    }

    public void updateComponents(EngineAPI api, GameState gs, float dt) {
        for(Component c : components) {
            c.update(api, gs, dt);
        }
    }

    public void renderComponents(EngineAPI api, Renderer r) {
        for(Component c : components) {
            c.render(api, r);
        }
    }

    public void collideComponents(GameObject other) {
        for(Component c : components) {
            c.collision(other);
        }
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public void removeComponent(String tag) {
        for(int i = 0; i < components.size(); i++) {
            if(components.get(i).getTag().equals(tag)) {
                components.remove(i);
            }
        }
    }


    public Component findComponent(String tag) {
        for (Component component : components) {
            if (component.getTag().equals(tag)) {
                return component;
            }
        }
        return null;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public float getOffX() {
        return offX;
    }

    public void setOffX(float offX) {
        this.offX = offX;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean showDebug() {
        return showDebug;
    }

    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getNamespacedID() {
        return this.namespace + ":" + this.identifier;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void decrementHealth() {
        this.health--;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }
}
