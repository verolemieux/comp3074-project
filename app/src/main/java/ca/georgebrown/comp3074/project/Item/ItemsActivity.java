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

    User validatedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        ListView itemList = findViewById(R.id.listItems);
        ArrayList<Item> items = validatedUser.Item_List;
        ItemAdapter adapter;
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
                startActivityForResult(editItemIntent, 2);
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
            validatedUser = (User)data.getSerializableExtra("ValidatedUser");
            ArrayList<Item> itemlist = (ArrayList<Item>) data.getSerializableExtra("ListItems");
            ArrayList<Item> items = itemlist;
            validatedUser.Item_List = items;
            ItemAdapter adapter;
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
                    startActivityForResult(editItemIntent, 2);
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
        if(requestCode == 2)
        {
            ListView itemList = findViewById(R.id.listItems);
            validatedUser = (User)data.getSerializableExtra("ValidatedUser");
            ArrayList<Item> itemlist = (ArrayList<Item>) data.getSerializableExtra("ListItems");
            Item editItem = (Item) data.getSerializableExtra("EditItem");
            ArrayList<Item> items = itemlist;
            validatedUser.Item_List = items;
            ItemAdapter adapter;
            ImageButton addItem = findViewById(R.id.btnAddItem);
            if(data.getSerializableExtra("Function").equals("Edit"))
            {
                for(Item i : items)
                {
                    if(i.getItem_Id() == editItem.getItem_Id())
                    {
                        i.setItem_Name(editItem.getItem_Name());
                        i.setDescription(editItem.getDescription());
                    }
                }
            }
            else
            {
                //Because for some reason remove object isn't working, below should be optimized
                ArrayList<Item> item2 = new ArrayList<Item>();

                for(Item i : items)
                {
                    if(i.getItem_Id() == editItem.getItem_Id())
                    {
                        continue;
                    }
                    item2.add(i);
                }
                items = item2;
            }
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
                    startActivityForResult(editItemIntent, 2);
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
        if(resultCode == 3)
        {
            ListView itemList = findViewById(R.id.listItems);
            validatedUser = (User)data.getSerializableExtra("ValidatedUser");
            ArrayList<Item> itemlist = (ArrayList<Item>) data.getSerializableExtra("ListItems");
            Item editItem = (Item) data.getSerializableExtra("EditItem");
            ArrayList<Item> items = itemlist;
            items.remove(editItem);
            validatedUser.Item_List = items;
            ItemAdapter adapter;
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
                    startActivityForResult(editItemIntent, 2);
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

    }
}
