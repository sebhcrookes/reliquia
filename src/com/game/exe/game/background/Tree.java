package com.game.exe.game.background;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;
import com.game.exe.game.entities.GameObject;
import com.game.exe.engine.gfx.Image;

public class Tree extends GameObject {

    private Image treeImage = new Image("/assets/blocks/environment/tree/temp.png");

    public Tree(int posX, int posY) {
        this.tag = "tree";
        this.posX = posX;
        this.posY = posY;
        this.isItem = false;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {}

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(treeImage, (int)posX * 16, (int)(posY * 16));
    }
}
