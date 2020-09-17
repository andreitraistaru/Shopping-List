package com.shoppinglist.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Entity(tableName = "shoppingLists")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String listName;
    private String shopName;
    private String otherInfo;
    private ArrayList<Item> items;

    public int getId() {
        return id;
    }
    public String getListName() {
        return listName;
    }
    public String getShopName() {
        return shopName;
    }
    public String getOtherInfo() {
        return otherInfo;
    }
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setListName(String listName) {
        this.listName = listName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addItem(Item newItem) {
        if (items == null) {
            items = new ArrayList<>();
        }

        items.add(newItem);
    }
    public void removeItem(Item currentItem) {
        items.remove(currentItem);
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("List's name: " + listName + "\n" + "Shop: " + (shopName.isEmpty() ? "-" : shopName) + "\n"
                + "Other info: " + (otherInfo.isEmpty() ? "-" : otherInfo) + "\n\n" + "Items:\n----------\n");

        if (items == null || items.isEmpty()) {
            stringBuilder.append("\t<empty>");
        } else {
            for (Item item : items) {
                stringBuilder.append("\t").append(item.toString()).append("\n");
            }
        }

        return stringBuilder.toString();
    }
}
