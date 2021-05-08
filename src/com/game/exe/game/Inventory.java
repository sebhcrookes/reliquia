package com.game.exe.game;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;

import java.io.Serializable;

import java.awt.event.KeyEvent;

public class Inventory implements Serializable{

    public int maxHealth = 10;
    public int health = 10;
    private Image healthImage;

    private int maxItems = 32;

    private int inventorySlotWidth = 9;
    private int inventorySlotHeight = 9;
    public int inventorySlots = 6;

    private int selectedSlot = 1;
    public String itemSelected = "";

    private GameManager gm;

    private Image inventory = new Image("/assets/ui/inventory.png");

    public String[] items;
    public int[] itemCount;

    public Inventory(GameManager gm) {
        this.gm = gm;
        healthUpdate();
    }

    public void update(GameManager gm, GameContainer gc) {
        if(items == null) {
            items = new String[inventorySlots];
            itemCount = new int[inventorySlots];
            for(int i = 0; i < items.length; i++) {
                items[i] = "";
                itemCount[i] = 0;
            }
        }

        itemSelected = items[selectedSlot - 1];

        if(gc.getInput().isKeyDown(gm.controls.drop)) {
            if(itemCount[selectedSlot - 1] > 0) {
                String item = items[selectedSlot - 1];
                int direction = 0;
                if(gm.player.facing == "right") { direction = 4; }
                if(gm.player.facing == "left") { direction = -4; }
                gm.entities.summonItem(item, gm.player.tileX, gm.player.tileY, direction, -2);
                gm.player.sound.dropSound.play();
                removeItem(items[selectedSlot - 1], 1);

            }
        }

        if(health < 0) { health = 0; }

        if(gm.controls.allowControls) {
            if (gc.getInput().isKey(KeyEvent.VK_1)) {
                selectedSlot = 1;
            } else if (gc.getInput().isKey(KeyEvent.VK_2)) {
                selectedSlot = 2;
            } else if (gc.getInput().isKey(KeyEvent.VK_3)) {
                selectedSlot = 3;
            } else if (gc.getInput().isKey(KeyEvent.VK_4)) {
                selectedSlot = 4;
            } else if (gc.getInput().isKey(KeyEvent.VK_5)) {
                selectedSlot = 5;
            } else if (gc.getInput().isKey(KeyEvent.VK_6)) {
                selectedSlot = 6;
            }
        }

        if(gc.getInput().isKeyDown(KeyEvent.VK_LEFT)) {
            health--;
            healthUpdate();
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_RIGHT)) {
            health++;
            healthUpdate();
        }

    }

    public void render(Renderer r, GameContainer gc) {
        r.setCamX(0);
        r.setCamY(0);

        int offX = (gc.getWidth() - inventory.getW()) / 2;
        int offY = 6 + gm.cinematicCount;

        r.drawImage(healthImage, (gc.getWidth() - healthImage.getW()) / 2, (gc.getHeight() - healthImage.getH() + 5) - offY);

        for(int i = 0; i < items.length; i++) {
            if(items[i] != "" && items[i] != null) {

                for(int n = 0; n < gm.entities.im.itemList.length; n++) {
                    if(items[i].equals(gm.entities.im.itemList[n].itemID)) {
                        r.drawImage(gm.entities.im.itemList[n].itemImage, offX + (int)((i * (inventorySlotWidth + 1)) + 1) + ((inventorySlotWidth - gm.entities.im.itemList[n].itemImage.getW()) / 2), ((gc.getHeight() - 1) - (inventorySlotHeight + gm.entities.im.itemList[n].itemImage.getH()) / 2) - offY);
                    }
                }
            }
            if(itemCount[i] != 0) {
                r.drawText(String.valueOf(itemCount[i]), offX + (i * (inventorySlotWidth + 1) + 1), ((gc.getHeight() - 1) - inventorySlotHeight * 2) - offY, 0xffffffff);
            }
        }

        r.drawImage(inventory, offX, (gc.getHeight() - inventory.getH()) - offY);
        r.drawRect(offX + (selectedSlot-1) * (inventorySlotWidth + 1), (gc.getHeight() - inventory.getH()) - offY, inventorySlotWidth + 1, inventorySlotHeight + 1, 0xffffffff);
    }

    public void pickup(String item) {

        for(int i = 0; i < items.length; i++) {
            try {
                if (items[i].equals(item)) {
                    if (itemCount[i] >= maxItems) {
                        continue;
                    } else {
                        itemCount[i]++;
                        gm.player.sound.pickupSound.play();
                        return;
                    }
                }
            }catch(Exception e) {}
            if(items[i].isEmpty() || items[i] == null) {
                if(itemCount[i] >= maxItems) {
                    return;
                }else {
                    items[i] = item;
                    itemCount[i]++;
                    gm.player.sound.pickupSound.play();
                    return;
                }
            }
        }
    }

    public void removeItem(String itemType, int count) {
        for(int i = 0; i < items.length; i++) {
            if(items[i].equals(itemType)) {
                itemCount[i] = itemCount[i] - count;
                if(itemCount[i] == 0) {
                    items[i] = "";
                }
                return;
            }
        }
    }

    public boolean hasItem(String itemType) {
        for(int i = 0; i < items.length; i++) {
            if(items[i].equals(itemType)) {
                return true;
            }
        }
        return false;
    }

    public boolean canStore(String itemType) {
        for(int i = 0; i < items.length; i++) {
            if(items[i] == itemType) {
                if(itemCount[i] >= maxItems) {
                    return false;
                }else{ return true; }
            }
        }
        return true;
    }

    public void healthUpdate() {
        try {
            healthImage = new Image("/assets/ui/health/" + health + ".png");
        }catch(Exception e) {}
    }

    public void playSound(String soundName) {
        return;
    }
}