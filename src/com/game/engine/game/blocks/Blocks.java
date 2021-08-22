package com.game.engine.game.blocks;

import java.util.ArrayList;

public class Blocks {

    public static ArrayList<Block> blocks = new ArrayList<>();

    public static void init() {
        blocks.add(new Block("air", 0, 0, false));
        blocks.add(new Block("generic_solid", 0xFF000000, 1, true));
        blocks.add(new Block("water", 0xFF5FBACC, 2, false));
        blocks.add(new Block("spawn", 0xFFFFF200, 3, false));
        blocks.add(new Block("end", 0xFFB038AA, 4, false));
    }

    public static String numberToString(int number) {
        for (Block block : blocks) {
            if (block.getNumber() == number) {
                return block.getName();
            }
        }
        return "";
    }

    public static int getNumber(int colour) {
        for (Block block : blocks) {
            if (block.getColourCode() == colour) {
                return block.getNumber();
            }
        }
        return 0;
    }

    public static Block getBlock(int number) {
        for (Block block : blocks) {
            if (block.getNumber() == number) {
                return block;
            }
        }
        return new Block("air", -1, 0, false);
    }

}
