package com.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.shoppinglist.R;
import com.shoppinglist.database.AppDatabase;
import com.shoppinglist.database.AppDatabaseClient;
import com.shoppinglist.database.AppDatabaseEntity;
import com.shoppinglist.shoppingLists.Item;

import java.util.ArrayList;
import java.util.List;

public class ChooseListActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);

        setTitle(getIntent().getStringExtra("actionTitle"));

        RecyclerView recyclerView = findViewById(R.id.listsRecyclerView_chooseListActivity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ListAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (items.isEmpty()) {
            Intent intent = new Intent(this, NewListActivity.class);

            startActivity(intent);
            finish();
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
        public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
            private TextView textView;

            private ListViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.nameList);
                textView.setOnClickListener(this);
                textView.setOnTouchListener(this);
            }

            public TextView getTextView() {
                return textView;
            }

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    textView.setBackgroundColor(Color.CYAN);
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
                    textView.setBackgroundColor(Color.TRANSPARENT);
                }

                return false;
            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditListActivity.class);

                intent.putExtra("listName", textView.getText().toString());

                startActivity(intent);
            }
        }

        private ListAdapter() {
            AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
            List<AppDatabaseEntity> lists = database.getAppDatabaseDao().getLists();
            items = new ArrayList<>(lists.size());

            for (AppDatabaseEntity list : lists) {
                items.add(list.getShoppingList().getListName());
            }
        }

        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View itemView = inflater.inflate(R.layout.activity_choose_list_list_entry, parent, false);

            return new ListViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            holder.getTextView().setText(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        AppDatabase database = AppDatabaseClient.getInstance(getApplicationContext()).getDatabase();
        List<AppDatabaseEntity> lists = database.getAppDatabaseDao().getLists();
        items.clear();

        for (AppDatabaseEntity list : lists) {
            items.add(list.getShoppingList().getListName());
        }

        adapter.notifyDataSetChanged();
    }
}
