package com.game.exe.game.serialisation;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.util.Logger;
import com.game.exe.game.GameManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveFiles {

    private GameContainer gc;
    private GameManager gm;

    private SerialisationManager sm;

    public SaveFiles(GameContainer gc, GameManager gm, SerialisationManager sm) {
        this.gc = gc;
        this.gm = gm;
        this.sm = sm;
    }

    /**
     * Gathers all required data and attempts to save
     * the game.
     */
    public void save() {
        try {
            createFolders();

            Object[] saveData = new Object[3];
            Object[] playerData = getPlayerData();
            Object[] inventoryData = getInventoryData();
            Object[] gameData = getGameData();
            saveData[0] = playerData;
            saveData[1] = inventoryData;
            saveData[2] = gameData;

            FileOutputStream itemOutStream = new FileOutputStream(new File(".game.exe/save" + sm.getSaveEnding()));
            ObjectOutputStream itemOut = new ObjectOutputStream(itemOutStream);
            itemOut.writeObject(saveData);
            itemOut.flush();
            itemOut.close();

            Logger.log(Logger.INFO, "Saved game successfully");
        }catch(Exception e) { Logger.log(Logger.ERROR, "Failed to save game"); }
    }

    /**
     * Gets specific player data in this format:
     * posX, posY, offX, offY, tileX, tileY, customEntityData, direction
     */
    private Object[] getPlayerData() {

        Object[] playerData = new Object[9];
        playerData[0] = this.gm.player.getPosX();
        playerData[1] = this.gm.player.getPosY();
        playerData[2] = this.gm.player.getOffX();
        playerData[3] = this.gm.player.getOffY();
        playerData[4] = this.gm.player.getTileX();
        playerData[5] = this.gm.player.getTileY();
        playerData[6] = this.gm.player.getCustomEntityData();
        playerData[7] = this.gm.player.getColour();
        playerData[8] = this.gm.player.getDirection();

        return playerData;
    }

    private Object[] getInventoryData() {
        Object[] inventoryData = new Object[2];
        inventoryData[0] = gm.inventory.items;
        inventoryData[1] = gm.inventory.itemCount;
        return inventoryData;
    }

    private Object[] getGameData() {
        Object[] gameData = new Object[1];
        gameData[0] = this.gm.levelNumber;
        return gameData;
    }

    private void createFolders() {
        File mainDir = new File(".game.exe/");
        mainDir.mkdir();
    }

}
