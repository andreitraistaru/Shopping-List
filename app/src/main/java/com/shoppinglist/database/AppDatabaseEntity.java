package com.shoppinglist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.shoppinglist.shoppingLists.ShoppingList;

@Entity(tableName = "DatabaseLists")
public class AppDatabaseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private ShoppingList shoppingList;

    public AppDatabaseEntity(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public int getId() {
        return id;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}