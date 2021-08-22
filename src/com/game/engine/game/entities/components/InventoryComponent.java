package com.game.engine.game.entities.components;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.gfx.Image;
import com.game.engine.game.states.GameState;
import com.game.engine.game.GlobalRandom;
import com.game.engine.game.entities.GameObject;
import com.game.engine.game.items.ArtifactItem;
import com.game.engine.game.items.Item;
import com.game.engine.game.items.ItemStack;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InventoryComponent extends Component {

    private GameObject parent;

    private final int INVENTORY_SIZE = 3;

    int slot = 0;

    private Image inventoryImage = new Image("/inventory/outline.png");
    private Image selectedImage = new Image("/inventory/selected.png");

    private ArrayList<ItemStack> items;

    public InventoryComponent(GameObject parent) {
        items = new ArrayList<>(INVENTORY_SIZE);
        for(int i = 0; i < INVENTORY_SIZE; i++) {
            items.add(ItemStack.getItemStack("game-exe:empty_item"));
        }
        this.tag = "inventory";
        this.parent = parent;
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        if(api.getInput().isKey(KeyEvent.VK_1)) {
            this.slot = 0;
        }
        if(api.getInput().isKey(KeyEvent.VK_2)) {
            this.slot = 1;
        }
        if(api.getInput().isKey(KeyEvent.VK_3)) {
            this.slot = 2;
        }

        if(api.getInput().getScroll() == 1) {
            if(this.slot != 0) {
                this.slot--;
            }
        } else if(api.getInput().getScroll() == -1) {
            if(this.slot != 2) {
                this.slot++;
            }
        }

        if(api.getInput().isKey(KeyEvent.VK_Q)) this.drop(gs, slot);

    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        int posX = (int)r.getCamX() + (api.getWidth() / 2) - (inventoryImage.getWidth() / 2);
        int posY = (int)r.getCamY() + api.getHeight() - inventoryImage.getHeight() - 7;

        r.drawImage(inventoryImage, posX, posY);

        int backOffX = posX + (slot * 15) + 1 + slot;
        int backOffY = posY + 1;
        if(slot == 1) backOffY += 6;
        r.drawImage(selectedImage, backOffX, backOffY);

        for(int i = 0; i < items.size(); i++) {
            try {

                int offX = posX + (i * 15) + 1 + (13 - items.get(i).getAbsoluteItem().getImage().getWidth()) / 2;
                int offY = posY + 1 + (14 - items.get(i).getAbsoluteItem().getImage().getHeight()) / 2;

                if(i != 0) offX += i;
                if(i == 1) offY += 6;

                r.drawImage(items.get(i).getAbsoluteItem().getImage(), offX, offY);
            } catch(NullPointerException ignored) {} // If the item has no image, ignore it
        }
    }

    @Override
    public void collision(GameObject other) {
        if(other instanceof Item && !other.isDead() && !(other instanceof ArtifactItem)) {
            if(isSpace() != -1 && ((Item) other).getPickupDelay() == 0) {
                other.setDead(true);
                items.set(isSpace(), new ItemStack(ItemStack.getItem(other.getNamespacedID())));
            }
        }
    }

    private void drop(GameState gs, int slot) {
        drop(gs, slot, parent.getFacing());
    }

    private void drop(GameState gs, int slot, int facing) {
        try {
            if (!items.get(slot).getNamespacedID().equals("game-exe:empty_item")) {

                Item i = items.get(slot).getAbsoluteItem().getClass().newInstance();
                i.setPosX(parent.getPosX());
                i.setPosY(parent.getPosY());
                i.setTileX(parent.getTileX());
                i.setTileY(parent.getTileY());

                switch (facing) {
                    case 0:
                        i.setVelX(10);
                        break;
                    case 1:
                        i.setVelX(-10);
                        break;
                }

                i.setPickupDelay(50);
                gs.getObjectManager().addObject(i);

                items.set(slot, ItemStack.getItemStack("game-exe:empty_item"));
            }
        } catch(InstantiationException | IllegalAccessException ignored) {}
    }

    public int isSpace() {
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).equals(ItemStack.getItemStack("game-exe:empty_item"))) {
                return i;
            }
        }
        return -1;
    }

    public void add(String namespacedID) {
        if(namespacedID.equals("game-exe:empty_item")) return;
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i) == ItemStack.getItemStack("game-exe:empty_item")) {
                items.set(i, new ItemStack(ItemStack.getItem(namespacedID)));
                return;
            }
        }
    }

    public ItemStack get(int index) {
        return items.get(index);
    }

    public ItemStack getHeldItem() {
        return items.get(slot);
    }

    public void deathDrop(GameState gs) {
        for (int i = 0; i < items.size(); i++) {
            drop(gs, i, GlobalRandom.nextInt(2) - 1);
        }
    }
}