package com.game.exe.game.level;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameState;

public class LevelManager {

    protected LevelLoader ll;
    protected LevelWeather lw;

    protected int levelW = 0, levelH = 0;
    private int levelNumber;

    public LevelManager(GameContainer gc, GameState gm) {
        ll = new LevelLoader(gc,gm, this); // Create the level loader manager
        lw = new LevelWeather(gc,gm,this); // Create the level weather manager
    }

    public void update(GameContainer gc, GameState gm, float dt) { lw.update(gc,gm,dt); } // Update the level's weather

    public LevelLoader getLevelLoader() { return ll; }
    public LevelWeather getLevelWeather() { return lw; }

    public int getLevelW() { return levelW; }
    public int getLevelH() { return levelH; }

    public void setLevelW(int levelW) { this.levelW = levelW; }
    public void setLevelH(int levelH) { this.levelH = levelH; }

    public int getLevelNumber() { return this.levelNumber; }
    public void setLevelNumber(int levelNumber) { this.levelNumber = levelNumber; }

    public void incrementLevelNumber() { this.levelNumber++; }
    public void decrementLevelNumber() { this.levelNumber--; }
}
