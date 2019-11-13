package ca.georgebrown.comp3074.project.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EditItemActivity extends BaseActivity {

    TextView itemName;
    TextView itemDesc;
    Item editItem;
    User validatedUser;
    ArrayList<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_edit_item, null, false);
        drawer.addView(contentView, 0);

        itemName = findViewById(R.id.txtItemName);
        itemDesc = findViewById(R.id.txtItemDescription);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        editItem = (Item) getIntent().getSerializableExtra("Item");
        list = (ArrayList<Item>) getIntent().getSerializableExtra("ListItems");
        //final ItemAdapter adapter = (ItemAdapter) getIntent().getSerializableExtra("Adapter");
        itemName.setText(editItem.getItem_Name());
        itemDesc.setText(editItem.getDescription());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem.setItem_Name(itemName.getText().toString());
                editItem.setDescription(itemDesc.getText().toString());
                Intent itemIntent = new Intent(v.getContext(), ItemsActivity.class);
                itemIntent.putExtra("ValidatedUser", validatedUser);
                itemIntent.putExtra("ListItems", list);
                itemIntent.putExtra("EditItem", editItem);
                itemIntent.putExtra("Function", "Edit");
                setResult(2, itemIntent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemIntent = new Intent(v.getContext(), ItemsActivity.class);
                itemIntent.putExtra("ValidatedUser", validatedUser);
                itemIntent.putExtra("ListItems", list);
                itemIntent.putExtra("EditItem", editItem);
                itemIntent.putExtra("Function", "Delete");
                setResult(2, itemIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
