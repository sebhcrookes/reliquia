package com.game.exe.game.background;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.position.Vector2;
import com.game.exe.game.GameState;
import com.game.exe.game.entities.GameObject;
import com.game.exe.engine.gfx.Image;

public class Tree extends GameObject {

    private Image treeImage = new Image("/assets/blocks/environment/tree/tree.png");

    public Tree(int posX, int posY) {
        this.tag = "tree";
        this.position = new Vector2(posX, posY);
        this.isItem = false;
    }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {}

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(treeImage, (int)position.getPosX() * 16, (int)(position.getPosY() * 16));
    }
}
