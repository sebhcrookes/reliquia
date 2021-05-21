package com.game.exe.game.serialisation;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.util.Logger;
import com.game.exe.game.GameManager;
import com.game.exe.game.entities.CustomEntityData;
import com.game.exe.game.entities.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadFiles {

    private GameContainer gc;
    private GameManager gm;

    private SerialisationManager sm;

    public LoadFiles(GameContainer gc, GameManager gm, SerialisationManager sm) {
        this.gc = gc;
        this.gm = gm;
        this.sm = sm;
    }

    /**
     * Gathers all required data and attempts to load
     * the game.
     */
    public void load() {
        try {
            FileInputStream fi = new FileInputStream(new File(".game.exe/save" + sm.getSaveEnding()));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Object[] saveData = (Object[]) oi.readObject();

            Object[] playerData = (Object[]) saveData[0];
            Object[] inventoryData = (Object[]) saveData[1];
            Object[] gameData = (Object[]) saveData[2];

            loadPlayerData(playerData);
            loadInventoryData(inventoryData);
            loadGameData(gameData);

            this.gm.loadingSucceeded = true;

            Logger.log(Logger.INFO, "Loaded game successfully");
        }catch(Exception e) { Logger.log(Logger.ERROR, "Failed to load game"); e.printStackTrace();}
    }

    /**
     * Loads specific player data in this format:
     * posX, posY, offX, offY, tileX, tileY, customEntityData
     */
    private void loadPlayerData(Object[] tempPlayerData) {

        String[] playerData = new String[tempPlayerData.length];

        for(int i = 0; i < tempPlayerData.length; i++) {
            playerData[i] = String.valueOf(tempPlayerData[i]);
        }

        this.gm.player = new Player(Float.parseFloat(playerData[0]), Float.parseFloat(playerData[1]), Float.parseFloat(playerData[2]), Float.parseFloat(playerData[3]));
        this.gm.player.setTileX(Integer.parseInt(playerData[4]));
        this.gm.player.setTileY(Integer.parseInt(playerData[5]));
        this.gm.player.setCustomEntityData((CustomEntityData)tempPlayerData[6]);
        this.gm.player.setColour(playerData[7]);
        this.gm.player.setDirection(playerData[8]);
    }

    private void loadInventoryData(Object[] inventoryData) {
        this.gm.inventory.items = (String[]) inventoryData[0];
        this.gm.inventory.itemCount = (int[]) inventoryData[1];
    }

    private void loadGameData(Object[] tempGameData) {
        String[] gameData = new String[1];

        for(int i = 0; i < tempGameData.length; i++) {
            gameData[i] = String.valueOf(tempGameData[i]);
        }

        this.gm.levelNumber = Integer.parseInt(gameData[0]);
    }

}
