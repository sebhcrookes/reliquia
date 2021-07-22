package com.game.exe.game.level;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.engine.util.PropertiesFile;
import com.game.exe.game.GameState;

public class LevelLoader {

    private GameState gm;
    private LevelManager lm;

    private PropertiesFile levelP;

    public String mapBasePath = "/assets/maps/";

    public LevelLoader(GameContainer gc, GameState gm, LevelManager lm) {
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
            int levelW = levelImage.getWidth();
            int levelH = levelImage.getHeight();

            lm.levelW = levelW;
            lm.levelH = levelH;

            gm.setCollision(new String[levelW * levelH]);
            for (int y = 0; y < levelImage.getHeight(); y++) {
                for (int x = 0; x < levelImage.getWidth(); x++) {
                    int currentBlock = x + y * levelImage.getWidth();

                    for (int i = 0; i < gm.blocks.blockList.size(); i++) {
                        if (levelImage.getPixels()[currentBlock] == gm.blocks.blockList.get(i).colourCode) {
                            gm.setCollisionValue(gm.blocks.blockList.get(i).blockID, currentBlock);
                        }
                    }
                    if (levelImage.getPixels()[currentBlock] == 0xffffff00) {
                        gm.spawnX = x;
                        gm.spawnY = y;
                    }
                    //Adding tops to bamboo
                    if(gm.getCollisionValue(currentBlock) == "bamboostem") {
                        if(gm.getCollisionValue(currentBlock - levelImage.getWidth()).equals("air")) {
                            gm.setCollisionValue("bambooshoot", currentBlock);
                        }
                    }
                    //Water
                    if(gm.getCollisionValue(currentBlock) == "water") {
                        if(gm.getCollisionValue(currentBlock - levelImage.getWidth()).equals("air")) {
                            gm.setCollisionValue("watersurface", currentBlock);
                        }
                    }

                    //region Grass Block
                    try {
                        if (gm.getCollisionFromID(gm.getCollisionValue(currentBlock)) && gm.getCollisionValue(currentBlock - 1).equals("grass_right")) {
                            gm.setCollisionValue("grass_block", currentBlock - 1);
                        }
                        if (gm.getCollisionValue(currentBlock) == "grass_block") {
                            for (int n = 0; n < gm.blocks.blockList.size(); n++) {
                                if (gm.blocks.blockList.get(n).blockID.equals(gm.getCollisionValue(currentBlock - 1))) {
                                    if (!gm.blocks.blockList.get(n).doesCollide) {
                                        gm.setCollisionValue("grass_left", currentBlock);
                                    }
                                }
                            }
                            if (gm.getCollisionValue(currentBlock) != "grass_left") {
                                gm.setCollisionValue("grass_right", currentBlock);
                            }
                        }

                        if (gm.getCollisionValue(currentBlock) == "dirtblock") {
                            if (gm.getCollisionValue(currentBlock - 1).equals("grass_right")) {
                                gm.setCollisionValue("grass_block", currentBlock - 1);
                            }
                        }
                    }catch(Exception e) {}
                    //endregion

                    //region Snowy Ground
                    try {
                        if (gm.getCollisionFromID(gm.getCollisionValue(currentBlock)) && gm.getCollisionValue(currentBlock - 1).equals("snowy_ground_right")) {
                            gm.setCollisionValue("snowy_ground", currentBlock - 1);
                        }
                        if (gm.getCollisionValue(currentBlock) == "snowy_ground") {
                            for (int n = 0; n < gm.blocks.blockList.size(); n++) {
                                if (gm.blocks.blockList.get(n).blockID.equals(gm.getCollisionValue(currentBlock - 1))) {
                                    if (!gm.blocks.blockList.get(n).doesCollide) {
                                        gm.setCollisionValue("snowy_ground_left", currentBlock);
                                    }
                                }
                            }
                            if (gm.getCollisionValue(currentBlock) != "snowy_ground_left") {
                                gm.setCollisionValue("snowy_ground_right", currentBlock);
                            }
                        }

                        if (gm.getCollisionValue(currentBlock) == "ground") {
                            if (gm.getCollisionValue(currentBlock - 1).equals("snowy_ground_right")) {
                                gm.setCollisionValue("snowy_ground", currentBlock - 1);
                            }
                        }
                    }catch(Exception e) {}
                    //endregion

                    //Tree Generation
                    if(gm.blocks.getGenericType(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock))) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - levelW))) {
                        if(gm.blocks.getGenericType(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-1))) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 1)))) {
                            if(gm.blocks.getGenericType(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-2))) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 2)))) {
                                if(gm.blocks.getGenericType(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-3))) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 3)))) {
                                    if(gm.blocks.getGenericType(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-4))) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 4)))) {
                                        if(gm.blocks.getGenericType(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-5))) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 5)))) {
                                            if(gm.blocks.getGenericType(gm.getBlockNumberFromID(gm.getCollisionValue(currentBlock-6))) == 1 && !gm.getCollisionFromID(gm.getCollisionValue(currentBlock - (levelW - 6)))) {
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
            e.printStackTrace();
            System.exit(0);
            gm.player.setLocation(gm.spawnX, gm.spawnY);
            lm.decrementLevelNumber();
            load(lm.getLevelNumber());
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

        //lm.getLevelWeather().setWeatherIntensity(Integer.parseInt(levelP.get("weatherIntensity")));

    }
}
