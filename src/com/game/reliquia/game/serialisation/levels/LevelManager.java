package com.game.reliquia.game.serialisation.levels;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.util.terminal.Console;
import com.game.reliquia.game.states.GameState;

public class LevelManager {

    private EngineAPI api;
    private GameState gs;

    private final int LEVEL_AMOUNT = 2;

    public static int spawnX, spawnY;

    private int[] collision;
    private Level[] levels;
    private int levelNumber = 0;

    private int sessionLoads = 0;

    public LevelManager(EngineAPI api, GameState gs) {
        this.api = api;
        this.gs = gs;
        this.levels = new Level[LEVEL_AMOUNT];
    }

    public void init(int levelNumber) {
        addLevel(new CampLevel(), 0);
        loadLevel(levelNumber);
    }

    public void update(EngineAPI api, GameState gs, float dt) {
        getLevel().update(api, gs, dt);
    }

    public Level getLevel() {
        return levels[levelNumber];
    }

    public void addLevel(Level level, int levelNum) {
        levels[levelNum] = level;
    }

    public void loadLevel(int levelNumber) {
        this.levelNumber = levelNumber;
        collision = getLevel().load(gs, api, "/levels/" + levelNumber + "/collision.png", "/levels/" + levelNumber + "/image.png");
        if(sessionLoads != 0 || gs.getObjectManager().getObject("reliquia:player").getTileX() == -1) { // If we are loading a new level
            gs.getObjectManager().getObject("reliquia:player").setTileX(LevelManager.spawnX);
            gs.getObjectManager().getObject("reliquia:player").setTileY(LevelManager.spawnY);
            gs.getObjectManager().getObject("reliquia:player").setOffX(0);
            gs.getObjectManager().getObject("reliquia:player").setOffY(0);
            gs.getObjectManager().getObject("reliquia:player").setFacing(0);
        }
        getLevel().onLoad(api, gs);
        sessionLoads++;
        Console.println("<green>LevelManager - <reset>Loaded level " + levelNumber);
    }

    public int getCollisionDetails(int x, int y) {
        if(x < 0 || x > getLevel().getLevelWidth() || y < 0 || y >= getLevel().getLevelHeight())
            return 1;
        return collision[x + y * levels[levelNumber].levelWidth];
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
