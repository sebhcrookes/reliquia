package com.game.exe.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Controls implements Serializable {

    public Controls() {}

    public int jump = KeyEvent.VK_SPACE;
    public int alternateJump = KeyEvent.VK_W;
    public int left = KeyEvent.VK_A;
    public int right = KeyEvent.VK_D;

    public int drop = KeyEvent.VK_Q;

    public int mainClick = MouseEvent.BUTTON1;
    public int secondaryClick = MouseEvent.BUTTON3;

    public boolean allowControls = true;
}
