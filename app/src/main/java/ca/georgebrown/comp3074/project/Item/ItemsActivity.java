package ca.georgebrown.comp3074.project.Item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.HomeActivity;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class ItemsActivity extends BaseActivity {

    User validatedUser;
    ArrayList<Item> items;
    ListView itemList;
    ItemAdapter adapter;
    EditText txtItemName;

    ItemsDAO itemTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemTable = new ItemsDAO(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_item, null, false);
        drawer.addView(contentView, 0);

        txtItemName = findViewById(R.id.txtItemName);

        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        itemList = findViewById(R.id.listItems);
        items = itemTable.getItems(validatedUser.getEmail());
        ImageButton addItem = findViewById(R.id.btnAddItem);
        adapter = new ItemAdapter(this, R.layout.item_layout, items, "apple");
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

        txtItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        if(requestCode == 2) {
            items = itemTable.getItems(validatedUser.getEmail());
            adapter.notifyDataSetChanged();
        }
        }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
