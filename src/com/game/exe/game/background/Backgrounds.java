package com.game.exe.game.background;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;
import com.game.exe.engine.gfx.Image;

public class Backgrounds {

    public Image tree = new Image("/assets/blocks/environment/tree/temp.png");

    private Tree[] trees = new Tree[100];
    private int treeCount = 0;

    private GameManager gm;

    public Backgrounds(GameManager gm) {
        this.gm = gm;
    }

    public void render(GameContainer gc, Renderer r) {
        if(gm.trees) {
            for (int i = 0; i < trees.length; i++) {
                if (trees[i] == null) {
                    return;
                }
                trees[i].render(gc, r);
            }
        }
    }

    public void createTree(int x, int y) {
        try {
            trees[treeCount] = new Tree(x, y - (tree.getH() / gm.TS));
            treeCount++;
        }catch(Exception e) {}
    }

}
