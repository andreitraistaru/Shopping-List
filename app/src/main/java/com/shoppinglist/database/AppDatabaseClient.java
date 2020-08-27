package com.shoppinglist.database;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseClient{
    private static AppDatabaseClient instance = null;
    private AppDatabase database = null;

    private AppDatabaseClient(Context context) {
        this.database = Room.databaseBuilder(context, AppDatabase.class, "shoppingListDatabase.db").allowMainThreadQueries().build();
    }

    public static AppDatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new AppDatabaseClient(context);
        }

        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
