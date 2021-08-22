package com.game.engine.game.items;

public class ItemStack {

    private static ItemStack[] items = new ItemStack[] {
            new ItemStack(new TestItem()),
            new ItemStack(new EmptyItem()),
            new ItemStack(new BambooSword())
    };

    private Item item;
    private String namespace;
    private String identifier;

    public ItemStack(Item item) {
        this.item = item;
        this.namespace = item.getNamespace();
        this.identifier = item.getIdentifier();
    }

    public static Item getItem(String namespacedID) {
        for (ItemStack itemStack : items) {
            if ((itemStack.getAbsoluteItem().getNamespacedID()).equals(namespacedID)) {
                return itemStack.getAbsoluteItem();
            }
        }
        return new EmptyItem();
    }

    public static ItemStack getItemStack(String namespacedID) {
        for (ItemStack itemStack : items) {
            if ((itemStack.getAbsoluteItem().getNamespacedID()).equals(namespacedID)) {
                return itemStack;
            }
        }
        return new ItemStack(new EmptyItem());
    }

    public Item getAbsoluteItem() {
        return item;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getNamespacedID() {
        return namespace + ":" + identifier;
    }
}
