package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.Home;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class ItemsActivity extends AppCompatActivity {

    User validatedUser;
    ArrayList<Item> items;
    ListView itemList;
    ItemAdapter adapter;
    ItemsDAO itemTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemTable = new ItemsDAO(this);

        setContentView(R.layout.activity_item);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        itemList = findViewById(R.id.listItems);
        items = itemTable.getItems(validatedUser.getEmail());
        ImageButton addItem = findViewById(R.id.btnAddItem);
        Button btnHome = findViewById(R.id.btnHome);
        adapter = new ItemAdapter(this, R.layout.item_layout, items);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long l) {
                Intent editItemIntent = new Intent(view.getContext(), EditItemActivity.class);
                editItemIntent.putExtra("ValidatedUser", validatedUser);
                Item editItem = (Item)parent.getItemAtPosition(position);
                editItemIntent.putExtra("Item", editItem);
                startActivityForResult(editItemIntent, 2);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(view.getContext(), AddItemActivity.class);
                addItemIntent.putExtra("ValidatedUser", validatedUser);
                startActivityForResult(addItemIntent, 1);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(view.getContext(), ca.georgebrown.comp3074.project.Home.class);
                Home.putExtra("ValidatedUser", validatedUser);
                startActivity(Home);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            items = itemTable.getItems(validatedUser.getEmail());
            adapter.notifyDataSetChanged();
        }
        if(requestCode == 2)
        {
            items = itemTable.getItems(validatedUser.getEmail());
            adapter.notifyDataSetChanged();
        }
    }
}
