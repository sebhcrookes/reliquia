package com.game.exe.game;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Controls implements Serializable {

    private final GameContainer gc;
    private final GameState gm;

    public int jump = KeyEvent.VK_SPACE;
    public int alternateJump = KeyEvent.VK_W;
    public int left = KeyEvent.VK_A;
    public int right = KeyEvent.VK_D;
    public int drop = KeyEvent.VK_Q;
    public int mainClick = MouseEvent.BUTTON1;
    public int secondaryClick = MouseEvent.BUTTON3;
    public boolean allowControls = true;

    private int varRadius = 0;

    public Controls(GameContainer gc, GameState gm) {
        this.gc = gc;
        this.gm = gm;
    }

    public void update(GameContainer gc, GameState gm, float dt) {
        if (varRadius != 0) {
            varRadius++;
        }
        if (varRadius == 10)
            varRadius = 0;
    }

    public void render(GameContainer gc, Renderer r) {
        if (gc.getInput().isButtonDown(mainClick))
            varRadius = 1;
        if (varRadius != 0)
            r.drawCircle((int) (gc.getInput().getMouseX() + gm.camera.getOffX()), (int) (gc.getInput().getMouseY() + gm.camera.getOffY()), varRadius, 0xffffffff);
    }
}
