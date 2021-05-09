package com.game.exe.game;

import com.game.exe.engine.util.Logger;

import java.io.*;

public class Serialiser implements Serializable {

    public String splitterChar = ",";
    private String fileEnding = ".save";

    public Serialiser(GameManager gm) {}

    public void createDirectories() {
        File mainDir = new File(".game.exe/");
        File playerDir = new File(".game.exe/player/");
        File invDir = new File(".game.exe/player/inventory/");
        mainDir.mkdir();
        playerDir.mkdir();
        invDir.mkdir();
    }

    public void savePlayer(GameManager gm) throws Exception {
        try{
            Logger.log(Logger.INFO, "Saving player");

            Object[] playerData = new Object[10];

            playerData[0] = gm.player.posX;
            playerData[1] = gm.player.posY;
            playerData[2] = gm.player.tileX;
            playerData[3] = gm.player.tileY;
            playerData[4] = gm.player.getOffX();
            playerData[5] = gm.player.getOffY();
            playerData[6] = gm.levelNumber;
            playerData[7] = gm.player.colour;
            playerData[8] = gm.sizeX;
            playerData[9] = gm.sizeY;

            FileOutputStream itemOutStream = new FileOutputStream(new File(".game.exe/player/player" + fileEnding));
            ObjectOutputStream itemOut = new ObjectOutputStream(itemOutStream);
            itemOut.writeObject(playerData);
            itemOut.flush();
            itemOut.close();
        } catch (Exception e) { Logger.log(Logger.ERROR, "Failed to save player"); }
    }

    public String[] loadPlayer(GameManager gm) {
        Logger.log(Logger.INFO, "Loading player");

        Object[] playerData = new Object[9];
        String[] playerDataString;

        try {
            FileInputStream fi = new FileInputStream(new File(".game.exe/player/player" + fileEnding));
            ObjectInputStream oi = new ObjectInputStream(fi);
            playerData = (Object[]) oi.readObject();
            playerDataString = new String[playerData.length];

            for(int i = 0; i < playerData.length; i++) {
                playerDataString[i] = String.valueOf(playerData[i]);
            }

            fi.close();
            oi.close();
        }catch(Exception e) { Logger.log(Logger.ERROR, "Failed to load player"); }

        playerDataString = new String[10];
        return playerDataString;
    }

    public void saveInventory(GameManager gm) {
        try{

            Logger.log(Logger.INFO, "Saving inventory");

            Object[] inventoryData = new Object[2];

            inventoryData[0] = gm.inventory.items;
            inventoryData[1] = gm.inventory.itemCount;

            FileOutputStream itemOutStream = new FileOutputStream(new File(".game.exe/player/inventory/inventory" + fileEnding));
            ObjectOutputStream itemOut = new ObjectOutputStream(itemOutStream);
            itemOut.writeObject(inventoryData);
            itemOut.flush();
            itemOut.close();
        }catch(Exception e) { Logger.log(Logger.ERROR, "Failed to save inventory"); }
    }

    public void loadInventory(GameManager gm) {
        try{

            Logger.log(Logger.INFO, "Loading inventory");

            Object[] inventoryData;

            FileInputStream fi = new FileInputStream(new File(".game.exe/player/inventory/inventory" + fileEnding));
            ObjectInputStream oi = new ObjectInputStream(fi);
            inventoryData = (Object[])oi.readObject();
            fi.close();
            oi.close();

            gm.inventory.items = (String[])inventoryData[0];
            gm.inventory.itemCount = (int[])inventoryData[1];
        }catch(Exception e) { Logger.log(Logger.ERROR, "Failed to load inventory"); }
    }
}
