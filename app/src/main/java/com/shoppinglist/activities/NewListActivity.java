package com.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shoppinglist.R;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.AppDatabaseClient;
import com.shoppinglist.database.AppDatabaseEntity;
import com.shoppinglist.shoppingLists.ShoppingList;

import java.util.List;

public class NewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        setTitle("New shopping list");

        TextInputEditText textBox = findViewById(R.id.listName_newListActivity);

        textBox.requestFocus();
    }

    public void createNewList(View view) {
        TextInputEditText listNameEditText = findViewById(R.id.listName_newListActivity);
        String listName = (listNameEditText.getText() == null) ? "" : listNameEditText.getText().toString();
        TextInputEditText shopNameEditText = findViewById(R.id.shopName_newListActivity);
        String shopName = (shopNameEditText.getText() == null) ? "" : shopNameEditText.getText().toString();
        TextInputEditText otherInformationEditText = findViewById(R.id.otherInformation_newListActivity);
        String otherInformation = (otherInformationEditText.getText() == null) ? "" : otherInformationEditText.getText().toString();

        if (listName.isEmpty()) {
            listNameEditText.setError("You did not enter the list's name!");
        } else {
            boolean validName = true;
            AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
            List<AppDatabaseEntity> lists = database.getAppDatabaseDao().getLists();

            for (AppDatabaseEntity list : lists) {
                if (list.getShoppingList().getListName().equals(listName)) {
                    listNameEditText.setError("List's name entered already used. Change it!");
                    validName = false;

                    break;
                }
            }

            if (validName) {
                ShoppingList newList = new ShoppingList(listName);
                newList.setShopName(shopName);
                newList.setOtherInfo(otherInformation);

                database.getAppDatabaseDao().insertItem(new AppDatabaseEntity(newList));
                Toast.makeText(getApplicationContext(), "Shopping list created!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, EditListActivity.class);
                intent.putExtra("listName", listName);

                startActivity(intent);
                finish();
            }
        }
    }
}
