package com.shoppinglist.shoppingLists;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class ShoppingListConverter {
    @TypeConverter
    public static ShoppingList toShoppingList(String convertedShoppingList) {
        return new Gson().fromJson(convertedShoppingList, ShoppingList.class);
    }

    @TypeConverter
    public static String toString(ShoppingList shoppingList) {
        return new Gson().toJson(shoppingList);
    }
}
