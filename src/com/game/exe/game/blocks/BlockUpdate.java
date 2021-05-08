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

        blockUnderPlayer = gm.getCollisionDetails(gm.player.tileX, gm.player.tileY + 1);
        playerBlock = gm.getCollisionDetails(gm.player.tileX, gm.player.tileY);


        //TODO: Spawning on mud makes speed exploit

        if (blockUnderPlayer == "mudblock") {
            if(!onMud) {
                gm.player.currentSpeed -= gm.player.SPEED / 2;
                gm.player.setJumpPower(gm.player.getJumpPower() - gm.player.getJUMP() / 2);
                onMud = true;
            }
        }else{
            if(onMud) {
                gm.player.currentSpeed += gm.player.SPEED / 2;
                gm.player.setJumpPower(gm.player.getJumpPower() + gm.player.getJumpPower() / 2);
                onMud = false;
            }
        }

        if(playerBlock == "water" || playerBlock == "watersurface") {
            gm.player.setUnderwater(true);
            System.out.println("Is Underwater!");
        }else{
            gm.player.setUnderwater(false);
        }

        //Portal Block
        if (playerBlock == gm.getBlockIDFromNumber(7)) {
            if((gm.levelAmount - 1) > gm.levelNumber) {
                gm.levelNumber++;
                gm.loadLevel(gm.levelNumber);
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
