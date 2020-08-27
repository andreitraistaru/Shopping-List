package com.shoppinglist.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.shoppinglist.shoppingLists.ShoppingListConverter;

@Database(entities = {AppDatabaseEntity.class}, version = 1)
@TypeConverters({ShoppingListConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDatabaseDao getAppDatabaseDao();
}