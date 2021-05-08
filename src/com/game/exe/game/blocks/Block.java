package com.game.exe.game.blocks;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;
import com.game.exe.engine.gfx.Image;

public class Block extends AbstractBlock {

    public Block(String pathToImage, String blockID, int colourCode, int relativeNumber, boolean doesCollide) {
        this.sprite = new Image(pathToImage);
        this.blockID = blockID;
        this.colourCode = colourCode;
        this.relativeNumber = relativeNumber;
        this.doesCollide = doesCollide;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {}

    @Override
    public void render(GameContainer gc, Renderer r) {}
}
