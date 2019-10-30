package ca.georgebrown.comp3074.project.Backpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.Item.ItemAdapter;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class AddBackpackActivity extends AppCompatActivity {

    ListView itemlist;
    Button btnAdd;
    User validatedUser;
    ArrayList<Backpack> userBackpacks;
    ArrayList<Item> items;
    ItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_backpack);

        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        userBackpacks = (ArrayList<Backpack>)getIntent().getSerializableExtra("UserBackpacks");
        items = validatedUser.Item_List;
        btnAdd = findViewById(R.id.btnAdd);
        itemlist = findViewById(R.id.listItems);
        adapter = new ItemAdapter(this, R.layout.item_layout, items);
        itemlist.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    Intent addBackpackIntent = new Intent(v.getContext(), BackpacksActivity.class);


                    setResult(1, addBackpackIntent);
                    finish();
                }

            }

        );
    }
}
