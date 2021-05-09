package com.game.exe.game.level;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.engine.util.PropertiesFile;
import com.game.exe.game.GameManager;

public class LevelLoader {

    private GameManager gm;
    private LevelManager lm;

    private PropertiesFile levelP;

    public String mapBasePath = "/assets/maps/";

    public LevelLoader(GameContainer gc, GameManager gm, LevelManager lm) {
        this.gm = gm;
        this.lm = lm;
    }

    public void load(int levelNumber) {
        String basePath = this.mapBasePath;
        String fileLocation = basePath + "map" + levelNumber + ".png";

        //Loading properties file for this level
        levelP = new PropertiesFile(basePath + "map" + levelNumber + ".prop");

        //Setting the weather for this level
        updateWeather();

        Image levelImage;
        int treeCount = 0;

        try {
            levelImage = new Image(fileLocation);
            int levelW = levelImage.getW();
            int levelH = levelImage.getH();

            lm.levelW = levelW;
            lm.levelH = levelH;

            gm.setCollision(new String[levelW * levelH]);
            for (int y = 0; y < levelImage.getH(); y++) {
                for (int x = 0; x < levelImage.getW(); x++) {
                    int currentBlock = x + y * levelImage.getW();

                    for (int i = 0; i < gm.blocks.blockList.size(); i++) {
                        if (levelImage.getP()[currentBlock] == gm.blocks.blockList.get(i).colourCode) {
                            gm.setCollisionValue(gm.blocks.blockList.get(i).blockID, currentBlock);
                        }
                    }
                    if (levelImage.getP()[currentBlock] == 0xffffff00) {
                        gm.spawnX = x;
                        gm.spawnY = y;
                    }
                    //Adding tops to bamboo
                    if(gm.getCollisionValue(currentBlock) == "bamboostem") {
                        if(gm.getCollisionValue(currentBlock - levelImage.getW()).equals("air")) {
                            gm.setCollisionValue("bambooshoot", currentBlock);
                        }
                    }
                    //Water
                    if(gm.getCollisionValue(currentBlock) == "water") {
                        if(gm.getCollisionValue(currentBlock - levelImage.getW()).equals("air")) {
                            gm.setCollisionValue("watersurface", currentBlock);
                        }
                    }

                    //region Grass Block
                    if(gm.getCollisionFromID(gm.getCollisionValue(currentBlock)) && gm.getCollisionValue(currentBlock - 1).equals("grassblockright")) {
                        gm.setCollisionValue("grassblock", currentBlock - 1);
                    }
                    if(gm.getCollisionValue(currentBlock) == "grassblock") {
                        for(int n = 0; n < gm.blocks.blockList.size(); n++) {
                            if(gm.blocks.blockList.get(n).blockID.equals(gm.getCollisionValue(currentBlock - 1))) {
                                if(!gm.blocks.blockList.get(n).doesCollide) {
                                    gm.setCollisionValue("grassblockleft", currentBlock);
                                }
                            }
                        }
                        if(gm.getCollisionValue(currentBlock) != "grassblockleft") {
                            gm.setCollisionValue("grassblockright", currentBlock);
                        }
                    }

                    if(gm.getCollisionValue(currentBlock) == "dirtblock") {
                        if(gm.getCollisionValue(currentBlock - 1).equals("grassblockright")) {
                            gm.setCollisionValue("grassblock", currentBlock-1);
                        }
                    }
                    //endregion

                    //Tree Generation
                    if(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock)) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - levelW))) {
                        if(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-1)) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 1)))) {
                            if(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-2)) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 2)))) {
                                if(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-3)) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 3)))) {
                                    if(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-4)) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 4)))) {
                                        if(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-5)) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 5)))) {
                                            if(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-6)) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 6)))) {
                                                if(treeCount < 2) {
                                                    gm.backgrounds.createTree(x - 6, y);
                                                    treeCount++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch(Exception e) {
            gm.player.setLocation(gm.spawnX, gm.spawnY);
            gm.levelNumber--;
            load(gm.levelNumber);
        }
    }

    private void updateWeather() {
        //Setting the weather
        switch(levelP.get("weather")) {
            case "clear":
                lm.getLevelWeather().set(LevelWeather.CLEAR);
                return;
            case "rain":
                lm.getLevelWeather().set(LevelWeather.RAIN);
                return;
            case "snow":
                lm.getLevelWeather().set(LevelWeather.SNOW);
        }
    }
}
