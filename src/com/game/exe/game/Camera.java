package com.game.exe.game;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.entities.GameObject;

public class Camera {

    private int offX, offY;

    private String targetTag;
    private GameObject target = null;

    private GameState gm;
    private GameContainer gc;

    public Camera(GameState gm, String tag) {
        this.gm = gm;
        this.targetTag = tag;
    }

    public void update(GameContainer gc, GameState gm, float dt) {
        if(target == null) {
            target = gm.getObject(targetTag);
        }

        if(target == null) {
            return;
        }

        if(gm.player.facing == "left") {
            offX = offX - (gc.getWidth() / 16) / 10;
        }else if(gm.player.facing == "right") {
            offX = offX + (gc.getWidth() / 16) / 10;
        }

        int targetX = (int)((target.getPosition().getPosX() + target.getWidth()/2) - gc.getWidth() / 2);
        int targetY = (int)((target.getPosition().getPosY() + target.getHeight()/2) - gc.getHeight() / 2);

        offX -= (dt * (offX - targetX) * 7);
        offY -= (dt * (offY - targetY) * 7);


        if(offX < 0) offX = 0;
        if(offY < 0) offY = 0;
        if(offX + gc.getWidth() > gm.lm.getLevelW() * gm.TS) offX = gm.lm.getLevelW() * gm.TS - gc.getWidth();
        if(offY + gc.getHeight() > gm.lm.getLevelH() * gm.TS) offY = gm.lm.getLevelH() * gm.TS - gc.getHeight();

        offY = offY - ((gc.getWidth() / 16) / 5);
    }

    public void render(Renderer r) {
        r.setCamX(offX);
        r.setCamY(offY);
    }


    public float getOffX() {
        return offX;
    }

    public void setOffX(int offX) {
        this.offX = offX;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(int offY) {
        this.offY = offY;
    }

    public String getTargetTag() {
        return targetTag;
    }

    public void setTargetTag(String targetTag) {
        this.targetTag = targetTag;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }
}
