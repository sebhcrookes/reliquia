package com.game.reliquia.game.serialisation.levels;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.gfx.Image;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.blocks.Blocks;
import com.game.reliquia.game.blocks.Tile;

public abstract class Level {

    protected String name;

    public int levelWidth;
    public int levelHeight;

    private Image image;

    public Level(String name) {
        this.name = name;
    }

    public abstract void onLoad(EngineAPI api, GameState gs);

    public int[] load(GameState gs, EngineAPI api, String path, String imagePath) {
        image = new Image(imagePath);
        Image levelImage = new Image(path);

        this.levelWidth = levelImage.getWidth();
        this.levelHeight = levelImage.getHeight();

        int[] collision = new int[levelWidth * levelHeight];

        int clearColour = levelImage.getP(0);
        int interpolationColour = levelImage.getP(1);
        api.setClearColour(clearColour);
        api.getRenderer().setInterpolationColour(interpolationColour);

        for(int y = 0; y < levelImage.getHeight(); y++) {
            for(int x = 0; x < levelImage.getWidth(); x++) {
                int index = x + y * levelWidth;

                collision[index] = Blocks.getNumber(levelImage.getP(index));

                if(Blocks.getBlock(collision[index]).doesCollide()) {
                    gs.getObjectManager().addObject(new Tile(x, y));
                }

                if(collision[index] == 3) {
                    LevelManager.spawnX = x;
                    LevelManager.spawnY = y;
                }
            }
        }

        for(int y = 0; y < levelHeight; y++) gs.getObjectManager().addObject(new Tile(-1, y));
        for(int y = 0; y < levelHeight; y++) gs.getObjectManager().addObject(new Tile(levelWidth, y));

        return collision;
    }

    public abstract void update(EngineAPI api, GameState gs, float dt);

    public void render(EngineAPI api, Renderer r) {
        r.drawImage(image, 0, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }
}

