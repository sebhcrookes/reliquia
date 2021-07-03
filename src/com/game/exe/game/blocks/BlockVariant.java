package com.game.exe.game.blocks;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameState;
import com.game.exe.engine.gfx.Image;

public class BlockVariant extends AbstractBlock {

    public BlockVariant(String pathToImage, String blockID, int relativeNumber, boolean doesCollide) {
        this.sprite = new Image(pathToImage);
        this.blockID = blockID;
        this.colourCode = 0;
        this.relativeNumber = relativeNumber;
        this.doesCollide = doesCollide;
    }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {}

    @Override
    public void render(GameContainer gc, Renderer r) {}
}