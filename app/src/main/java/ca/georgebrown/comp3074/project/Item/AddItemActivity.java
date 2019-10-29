package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class AddItemActivity extends AppCompatActivity {

    int maxId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Button btnSave = findViewById(R.id.btnSave);
        final TextView itemName = findViewById(R.id.txtItemName);
        final TextView itemDesc = findViewById(R.id.txtItemDescription);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        final ArrayList<Item> list = (ArrayList<Item>)getIntent().getSerializableExtra("ListItems");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(view.getContext(), ItemsActivity.class);
                for(Item i : list)
                {
                    maxId = i.maxValue(maxId, i.getItem_Id());
                }
                list.add(new Item(maxId+1, itemName.getText().toString(), itemDesc.getText().toString()));
                addItemIntent.putExtra("ValidatedUser", validatedUser);
                addItemIntent.putExtra("ListItems", list);
                setResult(1, addItemIntent);
                finish();
            }
        });
    }
}
