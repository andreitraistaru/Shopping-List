package com.shoppinglist.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.shoppinglist.shoppingLists.ShoppingList;

import java.util.List;

@Dao
public interface AppDatabaseDao {

    @Query("SELECT * FROM DatabaseLists")
    List<AppDatabaseEntity> getLists();

    @Insert
    void insertItem(AppDatabaseEntity newEntry);

    @Query("DELETE FROM DatabaseLists WHERE id = :id")
    void deleteItem(final int id);

    @Query("UPDATE DatabaseLists SET shoppingList =:list WHERE id = :id")
    void updateList(ShoppingList list, int id);
}
