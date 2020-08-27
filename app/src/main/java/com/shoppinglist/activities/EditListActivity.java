package com.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shoppinglist.R;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.AppDatabaseClient;
import com.shoppinglist.database.AppDatabaseEntity;
import com.shoppinglist.shoppingLists.Item;
import com.shoppinglist.shoppingLists.ShoppingList;

import java.util.List;

public class EditListActivity extends AppCompatActivity {

    private ShoppingList list;
    private int id;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        Intent intent = getIntent();

        setTitle("Shopping List: " + intent.getStringExtra("listName"));

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
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ItemHolder>{
        public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
            private TextView nameItem;
            private TextView quantityItem;

            public ItemHolder(@NonNull View itemHolder) {
                super(itemHolder);

                nameItem = itemHolder.findViewById(R.id.nameItem);
                nameItem.setOnClickListener(this);
                nameItem.setOnTouchListener(this);

                quantityItem = itemHolder.findViewById(R.id.quantityItem);
                quantityItem.setOnClickListener(this);
                quantityItem.setOnTouchListener(this);
            }

            public TextView getNameItem() {
                return nameItem;
            }

            public TextView getQuantityItem() {
                return quantityItem;
            }

            @Override
            public void onClick(View v) {
                TextInputEditText newItemName = findViewById(R.id.itemName_editListActivity);
                String newItemNameString = (newItemName.getText() == null) ? "" : newItemName.getText().toString();
                TextInputEditText newItemQuantity = findViewById(R.id.quantity_editListActivity);
                String newItemQuantityString = (newItemQuantity.getText() == null) ? "" : newItemQuantity.getText().toString();
                TextInputEditText newItemOtherInfo = findViewById(R.id.otherInformation_editListActivity);
                String newItemOtherInfoString = (newItemOtherInfo.getText() == null) ? "" : newItemOtherInfo.getText().toString();

                if (!newItemNameString.isEmpty() || !newItemQuantityString.isEmpty() || !newItemOtherInfoString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please add the current item or delete it before selecting from list!", Toast.LENGTH_SHORT).show();
                } else {
                    for (Item currentItem : list.getItems()) {
                        if (currentItem.getName().equals(nameItem.getText().toString())) {
                            newItemName.setText(currentItem.getName());
                            newItemQuantity.setText(currentItem.getQuantity());
                            newItemOtherInfo.setText(currentItem.getOtherInformation());

                            break;
                        }
                    }
                }
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    nameItem.setBackgroundColor(Color.CYAN);
                    quantityItem.setBackgroundColor(Color.CYAN);
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
                    nameItem.setBackgroundColor(Color.TRANSPARENT);
                    quantityItem.setBackgroundColor(Color.TRANSPARENT);
                }

                return false;
            }
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View itemView = inflater.inflate(R.layout.activity_edit_list_item_entry, parent, false);

            return new ItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.getNameItem().setText(list.getItems().get(position).getName());
            holder.getQuantityItem().setText(list.getItems().get(position).getQuantity());
        }

        @Override
        public int getItemCount() {
            return list.getItems().size();
        }
    }

    public void addItem(View view) {
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
    }

    public void removeItem(View view) {
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
                String message = new String(getResources().getString(R.string.share_general) + list.toString());

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (list.isEmpty()) {
            AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
            database.getAppDatabaseDao().deleteItem(id);
        }

        super.onBackPressed();
    }
}