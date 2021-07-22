package com.game.exe.game.blocks;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.game.GameState;
import com.game.exe.game.entities.GameObject;

public abstract class AbstractBlock extends GameObject {

    public Image sprite;
    public String blockID;
    public int colourCode;
    public double relativeNumber;
    public boolean doesCollide;

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {}

    @Override
    public void render(GameContainer gc, Renderer r) {}
}
