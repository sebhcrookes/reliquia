package com.game.exe.game.entities;

import com.game.exe.game.GameManager;
import com.game.exe.game.entities.items.Item;
import com.game.exe.game.entities.items.Items;
import com.game.exe.game.entities.npc.Beth;

import java.io.Serializable;

public class EntityManager implements Serializable {

    private GameManager gm;
    public Items im;

    public EntityManager(GameManager gm) {
        this.gm = gm;
        im = new Items(gm);
    }

    public void summonItem(String itemName, float posX, float posY, int velX, int velY) {
        for(int i = 0; i < im.itemList.length; i++) {
            if(itemName.equals(im.itemList[i].itemID)) {
                gm.objects.add(new Item(im.itemList[i].itemID, posX, posY, im.itemList[i].itemImage, true, im.itemList[i].fontPlaceholder, velX, velY));
            }
        }
        return;
    }

    public void summonMob(GameManager gm, String mobName, float posX, float posY) {
        if(mobName == "panda") {
            gm.objects.add(new Panda(posX, posY));
        }
        if(mobName == "beth") {
            gm.objects.add(new Beth(gm, posX, posY));
        }
    }
}
