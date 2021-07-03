package com.game.exe.game.blocks;

import java.io.Serializable;
import java.util.ArrayList;

public class Blocks implements Serializable {

    public ArrayList<AbstractBlock> blockList = new ArrayList<AbstractBlock>();

    public Blocks(){}

    public void initialise() {
        blockList.add(new Block("/assets/blocks/air.png", "air", 0xffffffff, 0, false));
        blockList.add(new Block("/assets/blocks/grass/grass.png", "grassblock", 0xff00ff00, 1, true));
        blockList.add(new Block("/assets/blocks/mud.png", "mudblock", 0xff894239, 1, true));
        blockList.add(new BlockVariant("/assets/blocks/grass/grassleft.png", "grassblockleft", 1, true));
        blockList.add(new BlockVariant("/assets/blocks/grass/grassright.png", "grassblockright", 1, true));
        blockList.add(new Block("/assets/blocks/grass/dirt.png", "dirtblock", 0xffb97a56, 2, true));

        blockList.add(new Block("/assets/blocks/environment/tallgrass.png", "tallgrassblock", 0xff0ed145, 3, false));
        blockList.add(new Block("/assets/blocks/environment/tulip.png", "flowerblock", 0xffec1c24, 4, false));
        blockList.add(new Block("/assets/blocks/grass/dirt.png", "stoneblock", 0xff999696, 5, true));
        blockList.add(new Block("/assets/blocks/portal.png", "portal", 0xffc800ff, 7, false));
        blockList.add(new Block("/assets/blocks/spawn.png", "spawnpoint", 0xffffff00, 8, false));
        blockList.add(new Block("/assets/blocks/barrel.png", "barrel", 0xff6d5645, 9, false));
        blockList.add(new Block("/assets/blocks/bamboostem.png", "bamboostem", 0xff1dc62b, 10, false));
        blockList.add(new Block("/assets/blocks/bambooshoot.png", "bambooshoot", 0, 11, false));
        blockList.add(new Block("/assets/blocks/environment/bush.png", "bush", 0xff733fcc,12, false));
        blockList.add(new Block("/assets/blocks/environment/rock.png", "rock", 0xffa0958c,13, false));
        blockList.add(new Block("/assets/blocks/water.png", "water", 0xff00a8f3,14, false));
        blockList.add(new Block("/assets/blocks/watersurface.png", "watersurface", 0,15, false));

        // Mountain
        blockList.add(new Block("/assets/blocks/mountainous/rock_wall.png", "rock_wall", 0xffaba098,16, false));
        blockList.add(new Block("/assets/blocks/mountainous/left_rock_wall.png", "left_rock_wall", 0xffab9898, 16, false));
        blockList.add(new Block("/assets/blocks/mountainous/right_rock_wall.png", "right_rock_wall", 0xffa9ab98, 16, false));

        blockList.add(new Block("/assets/blocks/mountainous/snowy_ground.png", "snowy_ground", 0xffdedede,17, true));
        blockList.add(new BlockVariant("/assets/blocks/mountainous/snowy_ground_left.png", "snowy_ground_left", 17, true));
        blockList.add(new BlockVariant("/assets/blocks/mountainous/snowy_ground_right.png", "snowy_ground_right", 17, true));
        blockList.add(new Block("/assets/blocks/mountainous/ground.png", "ground", 0xff615954,18, true));




    }
}
