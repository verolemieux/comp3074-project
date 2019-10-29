package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EditItemActivity extends AppCompatActivity {

    TextView itemName;
    TextView itemDesc;
    Item editItem;
    User validatedUser;
    ArrayList<Item> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemName = findViewById(R.id.txtItemName);
        itemDesc = findViewById(R.id.txtItemDescription);
        Button btnSave = findViewById(R.id.btnSave);
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
                setResult(2, itemIntent);
                finish();
            }
        });
    }
}
