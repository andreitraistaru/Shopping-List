package com.shoppinglist.shoppingLists;

import androidx.room.TypeConverter;

import java.util.ArrayList;

public class ShoppingList {
    private String listName;
    private String shopName;
    private String otherInfo;
    private ArrayList<Item> items;

    public ShoppingList(String listName) {
        this.listName = listName;
        this.items = new ArrayList<>();
    }

    public ShoppingList(ShoppingList list) {
        listName = list.listName;
        shopName = list.shopName;
        otherInfo = list.otherInfo;
        items = list.items;
    }

    public String getListName() {
        return listName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getShopName() {
        return shopName;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public void addItem(Item newitem) {
        items.add(newitem);
    }

    public void removeItem(Item currentItem) {
        items.remove(currentItem);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("List's name: " + listName + "\n" + "Shop: " + (shopName.isEmpty() ? "-" : shopName) + "\n"
                + "Other info: " + (otherInfo.isEmpty() ? "-" : otherInfo) + "\n\n" + "Items:\n----------\n");

        if (items.isEmpty()) {
            stringBuilder.append("\t<empty>");
        } else {
            for (Item item : items) {
                stringBuilder.append("\t").append(item.toString()).append("\n");
            }
        }

        return stringBuilder.toString();
    }
}
