package com.game.exe.game.entities.items;

import com.game.exe.game.GameState;

import java.io.Serializable;

public class Items implements Serializable {

    public GameState gm;

    public ItemFramework acid;
    public ItemFramework heartFragment;
    public ItemFramework coin;

    public ItemFramework[] itemList = new ItemFramework[3];

    public Items(GameState gm) {

        acid = new ItemFramework("acidbottle", gm.sprite.acidImage, "#");
        heartFragment = new ItemFramework("heartfragment", gm.sprite.heartImage, "%");
        coin = new ItemFramework("coin", gm.sprite.coinImage, "$");

        itemList[0] = acid;
        itemList[1] = heartFragment;
        itemList[2] = coin;
    }
}
