package com.shoppinglist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shoppinglist.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newList(View view) {
        Intent intent = new Intent(this, NewListActivity.class);

        startActivity(intent);
    }

    public void editList(View view) {
        Intent intent = new Intent(this, ChooseListActivity.class);

        intent.putExtra("actionTitle", "Choose a list to edit:");

        startActivity(intent);
    }

    public void goShopping(View view) {
        Intent intent = new Intent(this, ChooseListActivity.class);

        intent.putExtra("actionTitle", "Choose a list for shopping:");

        startActivity(intent);
    }
}
