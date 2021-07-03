package com.game.exe.game.ui;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameState;

public class UIManager {

    private GameContainer gc;
    private GameState gm;

    private static UIObject[] uiObjects;

    public UIManager(GameContainer gc, GameState gm) {
        this.gc = gc;
        this.gm = gm;

        int maxObjects = 100;
        uiObjects = new UIObject[maxObjects];
    }

    public void update(GameContainer gc, GameState gm, float dt) {
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

    public static void addUIObject(UIObject uiObject) {
        for (int i = 0; i < uiObjects.length; i++) {
            if (uiObjects[i] == null) {
                uiObjects[i] = uiObject;
                return;
            }
        }
    }

    public static UIObject getUIObject(String tag) {
        for(int i = 0; i < uiObjects.length; i++) {
            if(uiObjects[i].getTag() == tag) {
                return uiObjects[i];
            }
        }
        return null;
    }
}
