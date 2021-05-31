package com.game.exe.game.ui;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.game.GameManager;

public class UIManager {

    private GameManager gm;

    private UIObject[] uiObjects;

    public UIManager(GameManager gm) {
        this.gm = gm;

        int maxObjects = 100;
        uiObjects = new UIObject[maxObjects];
    }

    public void update(GameContainer gc, GameManager gm, float dt) {
        for(UIObject uiObject : uiObjects) {
            if (uiObject == null) {
                continue;
            } else {
                uiObject.update(gc, gm, dt);
            }
        }
    }

    public void render(GameContainer gc, Renderer r) {
        for(UIObject uiObject : uiObjects) {
            if(uiObject == null) {
                continue;
            } else {
                uiObject.render(gc, this.gm, r);
            }
        }
    }

    public void addUIOverlay(String tag, Image image, float posX, float posY, UICustomRender customRender) {
        for (int i = 0; i < uiObjects.length; i++) {
            if (uiObjects[i] == null) {
                uiObjects[i] = new UIObject(tag, image, posX, posY, customRender);
                return;
            }
        }
    }
}
