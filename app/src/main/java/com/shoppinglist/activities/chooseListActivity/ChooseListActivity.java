package com.shoppinglist.activities.chooseListActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.shoppinglist.R;
import com.shoppinglist.activities.NewListActivity;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.ShoppingList;

import java.util.List;

public class ChooseListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);

        setTitle(getString(R.string.activity_title_choose_list_activity));

        RecyclerView shoppingLists = findViewById(R.id.listsRecyclerView_chooseListActivity);
        shoppingLists.setLayoutManager(new LinearLayoutManager(this));
        final ListNameItemAdapter shoppingListsAdapter = new ListNameItemAdapter(this);
        shoppingLists.setAdapter(shoppingListsAdapter);

        AppDatabase.getInstance(this).getAppDatabaseDao().getLists().observe(this, new Observer<List<ShoppingList>>() {
            @Override
            public void onChanged(List<ShoppingList> data) {
                shoppingListsAdapter.setShoppingLists(data);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.choose_timetable_add_new_list) {
            startActivity(new Intent(this, NewListActivity.class));
        }

        return true;
    }
}
