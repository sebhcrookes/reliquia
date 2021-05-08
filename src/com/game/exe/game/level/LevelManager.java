package com.game.exe.game.level;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameManager;

public class LevelManager {

    private GameContainer gc;
    private GameManager gm;
    protected LevelLoader ll;
    protected LevelWeather lw;

    protected int levelW, levelH;

    public LevelManager(GameContainer gc, GameManager gm) {
        this.gc = gc;
        this.gm = gm;

        ll = new LevelLoader(gc,gm, this);
        lw = new LevelWeather(gc,gm,this);
    }

    public void update(GameContainer gc, float dt) { lw.update(gc,dt); }

    public LevelLoader getLevelLoader() { return ll; }
    public LevelWeather getLevelWeather() { return lw; }

    public int getLevelW() { return levelW; }
    public int getLevelH() { return levelH; }

    public void setLevelW(int levelW) { this.levelW = levelW; }
    public void setLevelH(int levelH) { this.levelH = levelH; }

}