package com.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shoppinglist.R;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.ShoppingList;

import java.util.List;

public class NewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        setTitle(getString(R.string.activity_title_new_list_activity));
    }

    public void createNewList(View view) {
        String listName = ((TextView) findViewById(R.id.listName_newListActivity)).getText().toString();
        String shopName = ((TextView) findViewById(R.id.shopName_newListActivity)).getText().toString();
        String otherInfo = ((TextView) findViewById(R.id.otherInformation_newListActivity)).getText().toString();

        if (listName.isEmpty()) {
            ((TextView) findViewById(R.id.listName_newListActivity)).setError(getString(R.string.no_list_name_error_new_list_activity));
        } else {
            final ShoppingList newList = new ShoppingList();

            newList.setListName(listName);
            newList.setShopName(shopName);
            newList.setOtherInfo(otherInfo);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(getApplicationContext()).getAppDatabaseDao().insertItem(newList);
                }
            }).start();

            finish();
        }
    }
}
