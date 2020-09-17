package com.shoppinglist.activities.editListActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private ListItemsItemAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        Intent intent = getIntent();
        final int shoppingListId = intent.getIntExtra(Constants.selectedShoppingListBundleKey, -1);

        RecyclerView items = findViewById(R.id.recyclerView_editListActivity);
        items.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListItemsItemAdapter(this);
        items.setAdapter(adapter);

        AppDatabase.getInstance(getApplicationContext()).getAppDatabaseDao().getLists(shoppingListId).observe(this, new Observer<ShoppingList>() {
            @Override
            public void onChanged(ShoppingList data) {
                shoppingList = data;

                adapter.setItems(data.getItems());

                setTitle(shoppingList.getListName());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(getApplicationContext()).getAppDatabaseDao().updateList(shoppingList);
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                String message = getResources().getString(R.string.share_general) + shoppingList.toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);

                break;
            case R.id.add_item:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(EditListActivity.this).inflate(R.layout.dialog_add_item, viewGroup, false);

                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                (dialogView.findViewById(R.id.save_dialog_add_item)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String itemName = ((TextView) dialogView.findViewById(R.id.name_dialog_add_item)).getText().toString();
                        String quantity = ((TextView) dialogView.findViewById(R.id.quantity_dialog_add_item)).getText().toString();
                        String info = ((TextView) dialogView.findViewById(R.id.info_dialog_add_item)).getText().toString();

                        if (itemName.isEmpty()) {
                            ((TextView) dialogView.findViewById(R.id.name_dialog_add_item)).setError(getString(R.string.no_item_name_error_add_item_dialog));
                        } else {
                            Item newItem = new Item();

                            newItem.setName(itemName);
                            newItem.setQuantity(quantity);
                            newItem.setOtherInformation(info);

                            shoppingList.addItem(newItem);
                            adapter.setItems(shoppingList.getItems());

                            alertDialog.dismiss();
                        }
                    }
                });

                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}