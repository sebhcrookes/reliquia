package com.game.exe.game.entities.items;

import com.game.exe.engine.gfx.Image;

import java.io.Serializable;

public class ItemFramework implements Serializable {

    public String tag;
    public String itemID;
    public Image itemImage;
    public String fontPlaceholder;

    public ItemFramework(String itemID, Image sprite, String fontPlaceholder) {
        this.tag = itemID;
        this.itemID = itemID;
        this.itemImage = sprite;
        this.fontPlaceholder = fontPlaceholder;
    }

}
