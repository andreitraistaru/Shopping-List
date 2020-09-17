package com.shoppinglist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDatabaseDao {
    @Query("SELECT * FROM shoppingLists")
    LiveData<List<ShoppingList>> getLists();

    @Query("SELECT * FROM shoppingLists WHERE id == :id")
    LiveData<ShoppingList> getLists(int id);

    @Insert
    void insertItem(ShoppingList shoppingList);

    @Delete
    void deleteItem(ShoppingList shoppingList);

    @Update
    void updateList(ShoppingList list);
}
