package com.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shoppinglist.Constants;
import com.shoppinglist.R;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.Item;
import com.shoppinglist.database.ShoppingList;

import java.util.List;

public class EditListActivity extends AppCompatActivity {
    private ShoppingList shoppingList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        Intent intent = getIntent();
        final int shoppingListId = intent.getIntExtra(Constants.selectedShoppingListBundleKey, -1);



        AppDatabase.getInstance(getApplicationContext()).getAppDatabaseDao().getLists(shoppingListId).observe(this, new Observer<ShoppingList>() {
            @Override
            public void onChanged(ShoppingList data) {
                shoppingList = data;



                setTitle(shoppingList.getListName());
            }
        });


        /*

        AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
        List<AppDatabaseEntity> lists = database.getAppDatabaseDao().getLists();

        for (AppDatabaseEntity currentList : lists) {
            if (currentList.getShoppingList().getListName().equals(intent.getStringExtra("listName"))) {
                list = currentList.getShoppingList();
                id = currentList.getId();

                break;
            }
        }

        RecyclerView recyclerView = findViewById(R.id.cardView_editListActivity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new CardAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        */
    }

    public void addItem(View view) {
        /*
        TextInputEditText newItemName = findViewById(R.id.itemName_editListActivity);
        String newItemNameString = (newItemName.getText() == null) ? "" : newItemName.getText().toString();
        TextInputEditText newItemQuantity = findViewById(R.id.quantity_editListActivity);
        String newItemQuantityString = (newItemQuantity.getText() == null) ? "" : newItemQuantity.getText().toString();
        TextInputEditText newItemOtherInfo = findViewById(R.id.otherInformation_editListActivity);
        String newItemOtherInfoString = (newItemOtherInfo.getText() == null) ? "" : newItemOtherInfo.getText().toString();

        if (newItemNameString.isEmpty()) {
            newItemName.setError("You did not enter the item's name!");
        } else {
            boolean validName = true;

            for (Item currentItem : list.getItems()) {
                if (currentItem.getName().equals(newItemNameString)) {
                    newItemName.setError("Item's name entered already exists. Change it!");
                    validName = false;

                    break;
                }
            }

            if (validName) {
                Item newItem = new Item(newItemNameString);
                newItem.setQuantity(newItemQuantityString);
                newItem.setOtherInformation(newItemOtherInfoString);

                list.addItem(newItem);

                AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
                database.getAppDatabaseDao().updateList(list, id);

                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "Item added!", Toast.LENGTH_SHORT).show();

                newItemName.getText().clear();
                newItemQuantity.getText().clear();
                newItemOtherInfo.getText().clear();
            }
        }
        */
    }

    public void removeItem(View view) {
        /*
        TextInputEditText newItemName = findViewById(R.id.itemName_editListActivity);
        String newItemNameString = (newItemName.getText() == null) ? "" : newItemName.getText().toString();
        TextInputEditText newItemQuantity = findViewById(R.id.quantity_editListActivity);
        String newItemQuantityString = (newItemQuantity.getText() == null) ? "" : newItemQuantity.getText().toString();
        TextInputEditText newItemOtherInfo = findViewById(R.id.otherInformation_editListActivity);
        String newItemOtherInfoString = (newItemOtherInfo.getText() == null) ? "" : newItemOtherInfo.getText().toString();

        if (newItemNameString.isEmpty() && newItemQuantityString.isEmpty() && newItemOtherInfoString.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select the item from list!", Toast.LENGTH_SHORT).show();
        } else {
            for (Item currentItem : list.getItems()) {
                if (currentItem.getName().equals(newItemNameString) && currentItem.getQuantity().equals(newItemQuantityString)
                && currentItem.getOtherInformation().equals(newItemOtherInfoString)) {

                    list.removeItem(currentItem);

                    AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
                    database.getAppDatabaseDao().updateList(list, id);

                    adapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Item removed!", Toast.LENGTH_SHORT).show();

                    newItemName.getText().clear();
                    newItemQuantity.getText().clear();
                    newItemOtherInfo.getText().clear();

                    return;
                }
            }

            Toast.makeText(getApplicationContext(), "Item not found in the list! Don't modify the item after selecting it from list!", Toast.LENGTH_LONG).show();
        }
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String message = getResources().getString(R.string.share_general) + shoppingList.toString();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}