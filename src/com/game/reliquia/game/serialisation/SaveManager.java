package com.game.reliquia.game.serialisation;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.util.EngineFile;
import com.game.reliquia.engine.util.terminal.Console;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.entities.components.InventoryComponent;
import com.game.reliquia.game.entities.player.Player;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveManager {

    private GameState gs;

    public SaveManager(GameState gs) {
        this.gs = gs;
    }

    public void save(EngineAPI api, GameState gs) {
        try {
            EngineFile folder = new EngineFile(".reliquia");
            folder.createDir();

            Object[] saveData = new Object[2];
            Object[] playerData = getPlayerData();
            Object[] gameData = getGameData();
            saveData[0] = playerData;
            saveData[1] = gameData;

            FileOutputStream itemOutStream = new FileOutputStream(".reliquia/save.reli");
            ObjectOutputStream itemOut = new ObjectOutputStream(itemOutStream);
            itemOut.writeObject(saveData);
            itemOut.flush();
            itemOut.close();

            Console.println("<green>SaveManager - <reset>Saved game successfully");
        }catch(Exception e) { Console.println("<orange>SaveManager - <reset>Failed to save game"); }
    }

    private Object[] getPlayerData() {

        Object[] playerData = new Object[10];
        Player p = (Player)this.gs.getObjectManager().getObject("reliquia:player");

        playerData[0] = p.getPosX();
        playerData[1] = p.getPosY();
        playerData[2] = p.getOffX();
        playerData[3] = p.getOffY();
        playerData[4] = p.getTileX();
        playerData[5] = p.getTileY();
        playerData[6] = p.getFacing();
        playerData[7] = p.getHealth();
        playerData[8] = p.getArtifacts().toArray();

        // Inventory Data
        InventoryComponent ic = (InventoryComponent) p.findComponent("inventory");
        String[] invData = new String[3];
        for(int i = 0; i < invData.length; i++) {
            invData[i] = ic.get(i).getNamespacedID();
        }

        playerData[9] = invData;

        return playerData;
    }

    private Object[] getGameData() {
        Object[] gameData = new Object[5];
        gameData[0] = this.gs.getLevelManager().getLevelNumber();
        gameData[1] = this.gs.getStatistics().getTimesPlayed();
        gameData[2] = this.gs.getStatistics().getDurationPlayed();

        gameData[3] = this.gs.getCamera().getOffX();
        gameData[4] = 0; // offY for the camera is stored as 0 so the camera has a cool load-in animation
        return gameData;
    }
}
