package com.shoppinglist.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShoppingListConverter {
    @TypeConverter
    public static ArrayList<Item> toArrayItems(String string) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Item>>(){}.getType();

        return gson.fromJson(string, type);
    }

    @TypeConverter
    public static String fromArrayItems(ArrayList<Item> items) {
        Gson gson = new Gson();

        return gson.toJson(items);
    }
}
