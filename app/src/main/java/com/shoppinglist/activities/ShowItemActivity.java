package com.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.shoppinglist.R;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.AppDatabaseClient;
import com.shoppinglist.database.AppDatabaseEntity;
import com.shoppinglist.shoppingLists.Item;
import com.shoppinglist.shoppingLists.ShoppingList;

import java.util.List;

public class ShowItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        String listIdString = getIntent().getStringExtra("listId");

        int listId = Integer.parseInt(listIdString);
        String itemName = getIntent().getStringExtra("itemName");

        AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
        List<AppDatabaseEntity> lists = database.getAppDatabaseDao().getLists();
        AppDatabaseEntity listEntity = null;

        for (AppDatabaseEntity list : lists) {
            if (list.getId() == listId) {
                listEntity = list;
                break;
            }
        }

        ShoppingList list = listEntity.getShoppingList();

        Item item = null;

        for (Item currentItem : list.getItems()) {
            if (currentItem.getName().equals(itemName)) {
                item = currentItem;

                break;
            }
        }

        setTitle(listEntity.getShoppingList().getListName() + ": " + itemName);

        TextView name = findViewById(R.id.itemName_showItem);
        TextView quantity = findViewById(R.id.quantity_showItem);
        TextView otherInfo = findViewById(R.id.otherInformation_showItem);

        name.setText(item.getName());
        quantity.setText(item.getQuantity());
        otherInfo.setText(item.getOtherInformation());
    }
}
