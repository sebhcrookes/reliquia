package com.game.reliquia.game.serialisation;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.util.terminal.Console;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.Statistics;
import com.game.reliquia.game.entities.components.InventoryComponent;
import com.game.reliquia.game.entities.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadManager {

    private GameState gs;

    public LoadManager(GameState gs) {
        this.gs = gs;
    }

    public Object[] load(EngineAPI api, GameState gs) {
        try {
            FileInputStream fi = new FileInputStream(new File(".reliquia/save.reli"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Object[] saveData = (Object[]) oi.readObject();

            Console.println("<green>LoadManager - <reset>Loaded game successfully");

            return saveData;
        }catch(Exception e) { Console.println("<orange>LoadManager - <reset>Failed to load game"); }

        return null;
    }

    public void loadValues(GameState gs, Object[] loadedObject) {
        Object[] playerData = (Object[])loadedObject[0];
        Object[] gameData = (Object[])loadedObject[1];
        gs.getObjectManager().getObject("reliquia:player").setPosX((float) playerData[0]);
        gs.getObjectManager().getObject("reliquia:player").setPosY((float) playerData[1]);
        gs.getObjectManager().getObject("reliquia:player").setOffX((float) playerData[2]);
        gs.getObjectManager().getObject("reliquia:player").setOffY((float) playerData[3]);
        gs.getObjectManager().getObject("reliquia:player").setTileX((int) playerData[4]);
        gs.getObjectManager().getObject("reliquia:player").setTileY((int) playerData[5]);
        gs.getObjectManager().getObject("reliquia:player").setFacing((int) playerData[6]);
        gs.getObjectManager().getObject("reliquia:player").setHealth((double) playerData[7]);

        // Artifacts
        Player p = (Player)gs.getObjectManager().getObject("reliquia:player");

        String[] unlocked = (String[])playerData[8];

        for(String artifact : unlocked) {
            p.getArtifacts().silentlyUnlock(artifact);
        }

        if(unlocked.length != 0) {
            Console.println("<green>Artifacts - <reset>Loaded saved artifacts");
        }

        // Inventory Data
        InventoryComponent ic = (InventoryComponent) p.findComponent("inventory");
        String[] invData = (String[]) playerData[9];
        for(int i = 0; i < invData.length; i++) {
            ic.add(invData[i]);
        }
        // Game Data
        gs.setStatistics(new Statistics((int) gameData[1]));
        gs.getStatistics().setDurationPlayed((long) gameData[2]);

        gs.getCamera().setOffX((int) gameData[3]);
        gs.getCamera().setOffY((int) gameData[4]);
    }
}
