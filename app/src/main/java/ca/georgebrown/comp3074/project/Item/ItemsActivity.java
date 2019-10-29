package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        ListView itemList = findViewById(R.id.listItems);
        final ArrayList<Item> items = validatedUser.Item_List;
        final ItemAdapter adapter;
        ImageButton addItem = findViewById(R.id.btnAddItem);
        adapter = new ItemAdapter(this, R.layout.item_layout, items);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long l) {
                Intent editItemIntent = new Intent(view.getContext(), EditItemActivity.class);
                editItemIntent.putExtra("ValidatedUser", validatedUser);
                editItemIntent.putExtra("ListItems", validatedUser.Item_List);
                Item editItem = (Item)parent.getItemAtPosition(position);
                editItemIntent.putExtra("Item", editItem);
                startActivityForResult(editItemIntent, 1);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(view.getContext(), AddItemActivity.class);
                addItemIntent.putExtra("ValidatedUser", validatedUser);
                addItemIntent.putExtra("ListItems", validatedUser.Item_List);
                startActivityForResult(addItemIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            ListView itemList = findViewById(R.id.listItems);
            User validatedUser = (User)data.getSerializableExtra("ValidatedUser");
            ArrayList<Item> itemlist = (ArrayList<Item>) data.getSerializableExtra("ItemList");
            final ArrayList<Item> items = itemlist;
            final ItemAdapter adapter;
            adapter = new ItemAdapter(this, R.layout.item_layout, items);
            itemList.setAdapter(adapter);
        }
    }
}
