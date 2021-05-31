package com.game.exe.game.ui;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.game.GameManager;
import javafx.beans.property.adapter.ReadOnlyJavaBeanDoubleProperty;

public class UIObject {

    private String tag;
    private Image image;
    private float posX;
    private float posY;
    private UICustomRender customRender;

    public UIObject(String tag, Image image, float posX, float posY, UICustomRender customRender) {
        this.tag = tag;
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.customRender = customRender;
    }

    public void update(GameContainer gc, GameManager gm, float dt) {
        this.customRender.update(gc,gm,dt,this);
    }

    public void render(GameContainer gc, GameManager gm, Renderer r) {
        this.customRender.render(gc,gm,r,this);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public UICustomRender getCustomRender() {
        return customRender;
    }

    public void setCustomRender(UICustomRender customRender) {
        this.customRender = customRender;
    }
}
