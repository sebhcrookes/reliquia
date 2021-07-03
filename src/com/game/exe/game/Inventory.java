package com.game.exe.game;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.game.ui.UIManager;
import com.game.exe.game.ui.UIObject;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Arrays;

public class Inventory implements Serializable {

    public int inventorySlots = 6;
    public int selectedSlot = 1;
    public String itemSelected = "";
    public String[] items;
    public int[] itemCount;
    private final int MAX_ITEMS = 32;
    private final int SLOT_WIDTH = 9;
    private final int SLOT_HEIGHT = 9;
    private final GameManager gm;

    public Inventory(GameManager gm) {
        this.gm = gm;

        items = new String[inventorySlots];
        itemCount = new int[inventorySlots];

        Arrays.fill(items, "");
        Arrays.fill(itemCount, 0);

        InventoryUI inventoryUI = new InventoryUI();
        UIManager.addUIObject(inventoryUI);
    }

    public void update(GameManager gm, GameContainer gc) {

        if (items == null) {
            items = new String[inventorySlots];
            itemCount = new int[inventorySlots];
            for (int i = 0; i < items.length; i++) {
                items[i] = "";
                itemCount[i] = 0;
            }
        }

        itemSelected = items[selectedSlot - 1];

        if (gc.getInput().isKeyDown(gm.controls.drop)) {
            if (itemCount[selectedSlot - 1] > 0) {
                String item = items[selectedSlot - 1];
                int direction = 0;
                if (gm.player.facing.equals("right")) {
                    direction = 4;
                }
                if (gm.player.facing.equals("left")) {
                    direction = -4;
                }
                gm.em.summonItem(item, gm.player.getTileX(), gm.player.getTileY(), direction, -2);
                gm.player.sound.dropSound.play();
                removeItem(items[selectedSlot - 1], 1);

            }
        }

        if (gm.controls.allowControls) {
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
    }

    public void render(Renderer r, GameContainer gc) {}

    public void pickup(String item) {

        for (int i = 0; i < items.length; i++) {
            try {
                if (items[i].equals(item)) {
                    if (itemCount[i] >= MAX_ITEMS) {
                        continue;
                    } else {
                        itemCount[i]++;
                        gm.player.sound.pickupSound.play();
                        return;
                    }
                }
            } catch (Exception ignored) {
            }
            if (items[i].isEmpty() || items[i] == null) {
                if (itemCount[i] >= MAX_ITEMS) {
                    return;
                } else {
                    items[i] = item;
                    itemCount[i]++;
                    gm.player.sound.pickupSound.play();
                    return;
                }
            }
        }
    }

    public void removeItem(String itemType, int count) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(itemType)) {
                itemCount[i] = itemCount[i] - count;
                if (itemCount[i] == 0) {
                    items[i] = "";
                }
                return;
            }
        }
    }

    public boolean hasItem(String itemType) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(itemType)) {
                return true;
            }
        }
        return false;
    }

    public boolean canStore(String itemType) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(itemType)) {
                return itemCount[i] < MAX_ITEMS;
            }
        }
        return true;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public int getSlotWidth() { return this.SLOT_WIDTH; }

    public int getSlotHeight() { return this.SLOT_HEIGHT; }
}

/**
 * game.exe: Inventory UI class
 * Contains all UI-related inventory things
 */
class InventoryUI extends UIObject {

    private Image inventory = new Image("/assets/ui/inventory.png");

    public InventoryUI() {
        this.setTag("inventory");
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        this.setPosX((gc.getWidth() - inventory.getW()) >> 1);
        this.setPosY((gc.getHeight() - inventory.getH()) - (6 + gm.cinematicCount));
    }

    @Override
    public void render(GameContainer gc, GameManager gm, Renderer r) {

        // Inventory Outline
        r.drawImage(inventory, (int) this.getPosX(), (int) this.getPosY());

        // Inventory Slot Selector
        r.drawRect((gc.getWidth() - inventory.getW()) / 2 + (gm.inventory.getSelectedSlot() - 1) * (gm.inventory.getSlotWidth() + 1),
                (gc.getHeight() - inventory.getH()) - 6 + gm.cinematicCount,
                gm.inventory.getSlotWidth() + 1,
                gm.inventory.getSlotHeight() + 1,
                0xffffffff);

        // Item Text
        for (int i = 0; i < gm.inventory.items.length; i++) {
            if (gm.inventory.itemCount[i] != 0) {
                r.drawText(String.valueOf(gm.inventory.itemCount[i]),
                        (gc.getWidth() - inventory.getW()) / 2 + (i * (gm.inventory.getSlotWidth() + 1) + 1),
                        ((gc.getHeight() - 1) - gm.inventory.getSlotHeight() * 2) - 6 + gm.cinematicCount,
                        0xffffffff);
            }
        }

        // Images
        for (int i = 0; i < gm.inventory.items.length; i++) {
            if (gm.inventory.items[i] != "" && gm.inventory.items[i] != null) {

                for (int n = 0; n < gm.em.im.itemList.length; n++) {
                    if (gm.inventory.items[i].equals(gm.em.im.itemList[n].itemID)) {
                        r.drawImage(gm.em.im.itemList[n].itemImage,
                                (gc.getWidth() - inventory.getW()) / 2 + (i * (gm.inventory.getSlotWidth() + 1)) + 1 + ((gm.inventory.getSlotWidth() - gm.em.im.itemList[n].itemImage.getW()) / 2),
                                ((gc.getHeight() - 1) - (gm.inventory.getSlotHeight() + gm.em.im.itemList[n].itemImage.getH()) / 2) - (6 + gm.cinematicCount));
                    }
                }
            }
        }
    }
}