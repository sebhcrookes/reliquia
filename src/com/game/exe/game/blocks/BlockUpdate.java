package com.game.exe.game.blocks;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameState;

import java.io.Serializable;

public class BlockUpdate implements Serializable {

    private String blockUnderPlayer = "";
    private String playerBlock = "";

    private boolean onMud = false;
    private boolean inWater = false;


    public void update(GameContainer gc, GameState gm, float dt) {

        blockUnderPlayer = gm.getCollisionDetails(gm.player.getTileX(), gm.player.getTileY() + 1);
        playerBlock = gm.getCollisionDetails(gm.player.getTileX(), gm.player.getTileY());

        //TODO: Add in mud slowness

        //Portal Block
        if (playerBlock == gm.getBlockIDFromNumber(7)) {
            gm.lm.incrementLevelNumber();
            gm.lm.getLevelLoader().load(gm.lm.getLevelNumber());
            gm.player.setLocation(gm.spawnX, gm.spawnY);
        }

        //Barrels
        if(gm.getCollisionDetails(gm.ui.mouseTileX, gm.ui.mouseTileY) == "barrel") {
            if(gc.getInput().isButtonDown(gm.controls.mainClick)) {
                gm.setBlock(gm.ui.mouseTileX, gm.ui.mouseTileY, "air");

                gm.em.summonItem("heartfragment", gm.ui.mouseTileX + gm.random.generate(-2,2), gm.ui.mouseTileY - 1, 0, -2);
                gm.em.summonItem("acidbottle", gm.ui.mouseTileX + gm.random.generate(-2,2), gm.ui.mouseTileY - 1, 0, -2);
                gm.em.summonItem("coin", gm.ui.mouseTileX + gm.random.generate(-2,2), gm.ui.mouseTileY - 1, 0, -2);
            }
        }
    }
}
