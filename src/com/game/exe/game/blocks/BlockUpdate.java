package com.game.exe.game.blocks;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameManager;

import java.io.Serializable;

public class BlockUpdate implements Serializable {

    private String blockUnderPlayer = "";
    private String playerBlock = "";

    private boolean onMud = false;
    private boolean inWater = false;


    public void update(GameContainer gc, GameManager gm, float dt) {

        blockUnderPlayer = gm.getCollisionDetails(gm.player.getTileX(), gm.player.getTileY() + 1);
        playerBlock = gm.getCollisionDetails(gm.player.getTileX(), gm.player.getTileY());

        //TODO: Add in mud slowness

        //Portal Block
        if (playerBlock == gm.getBlockIDFromNumber(7)) {
            if((gm.levelAmount - 1) > gm.levelNumber) {
                gm.levelNumber++;
                gm.lm.getLevelLoader().load(gm.levelNumber);
                gm.player.setLocation(gm.spawnX, gm.spawnY);
            }
        }

        //Barrels
        if(gm.getCollisionDetails(gm.ui.mouseTileX, gm.ui.mouseTileY) == "barrel") {
            if(gc.getInput().isButtonDown(gm.controls.mainClick)) {
                gm.setBlock(gm.ui.mouseTileX, gm.ui.mouseTileY, "air");

                gm.entities.summonItem("heartfragment", gm.ui.mouseTileX + gm.random.generate(-2,2), gm.ui.mouseTileY - 1, 0, -2);
                gm.entities.summonItem("acidbottle", gm.ui.mouseTileX + gm.random.generate(-2,2), gm.ui.mouseTileY - 1, 0, -2);
                gm.entities.summonItem("coin", gm.ui.mouseTileX + gm.random.generate(-2,2), gm.ui.mouseTileY - 1, 0, -2);
            }
        }
    }
}
