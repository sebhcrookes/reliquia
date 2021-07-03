package com.game.exe.game.serialisation;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameState;

public class SerialisationManager {

    private GameContainer gc;
    private GameState gm;

    private SaveFiles sf;
    private LoadFiles lf;

    private String saveEnding = ".save";

    public SerialisationManager(GameContainer gc, GameState gm) {
        this.gc = gc;
        this.gm = gm;

        sf = new SaveFiles(gc, gm, this);
        lf = new LoadFiles(gc, gm, this);
    }

    public void loadGame() {
        this.lf.load();
    }

    public void saveGame() {
        this.sf.save();
    }

    public String getSaveEnding() {
        return this.saveEnding;
    }

    public void createFolders() { sf.createFolders(); }
}
