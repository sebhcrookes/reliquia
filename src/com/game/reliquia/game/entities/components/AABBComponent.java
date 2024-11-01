package com.game.reliquia.game.entities.components;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.game.Physics;
import com.game.reliquia.game.entities.GameObject;
import com.game.reliquia.game.states.GameState;

public class AABBComponent extends Component {

    private GameObject parent;

    private int centerX, centerY;
    private int halfWidth, halfHeight;

    private int lastCenterX, lastCenterY;

    public AABBComponent(GameObject parent) {
        this.tag = "aabb";
        this.parent = parent;
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        lastCenterX = centerX;
        lastCenterY = centerY;

        centerX = (int) (parent.getPosX() + (parent.getWidth() / 2));
        centerY = (int) (parent.getPosY() + (parent.getHeight() / 2) + (parent.getPaddingTop() / 2) - (parent.getPaddingBottom() / 2));

        halfWidth = (parent.getWidth() / 2) - parent.getPadding();
        halfHeight = (parent.getHeight() / 2) - (parent.getPaddingTop() / 2) - (parent.getPaddingBottom() / 2);

        Physics.addAABBComponent(this);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        if(api.getSettings().isDebug() && parent.showDebug()) {
            r.drawRect(centerX - halfWidth, centerY - halfHeight, halfWidth * 2, halfHeight * 2, 0xFF000000);

            int facingDirection = 0xFFFFF200;

            if (parent.getFacing() == 0) {
                r.drawFillRect((getParent().getPosX() + parent.getWidth()), getParent().getPosY() + (getParent().getHeight() >> 1), 5, 0, facingDirection);
            } else if (parent.getFacing() == 1) {
                r.drawFillRect(getParent().getPosX() - 5, getParent().getPosY() + (getParent().getHeight() >> 1), 5, 0, facingDirection);
            }
        }
    }

    @Override
    public void collision(GameObject other) {}

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getHalfWidth() {
        return halfWidth;
    }

    public void setHalfWidth(int halfWidth) {
        this.halfWidth = halfWidth;
    }

    public int getHalfHeight() {
        return halfHeight;
    }

    public void setHalfHeight(int halfHeight) {
        this.halfHeight = halfHeight;
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public int getLastCenterX() {
        return lastCenterX;
    }

    public void setLastCenterX(int lastCenterX) {
        this.lastCenterX = lastCenterX;
    }

    public int getLastCenterY() {
        return lastCenterY;
    }

    public void setLastCenterY(int lastCenterY) {
        this.lastCenterY = lastCenterY;
    }
}
