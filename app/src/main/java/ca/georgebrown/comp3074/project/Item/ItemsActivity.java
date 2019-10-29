package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
        ItemAdapter adapter2;
        final ItemAdapter adapter;
        try{
            adapter2 = (ItemAdapter)getIntent().getSerializableExtra("Adapter");
        }
        catch(Exception e)
        {
            adapter2 = new ItemAdapter(this, R.layout.item_layout, items);
        }
        adapter = adapter2;
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long l) {
                Intent editItemIntent = new Intent(view.getContext(), EditItemActivity.class);
                editItemIntent.putExtra("User", validatedUser);
                Item editItem = (Item)parent.getItemAtPosition(position);
                editItemIntent.putExtra("Item", editItem);
                editItemIntent.putExtra("Adapter", adapter);
                startActivity(editItemIntent);
            }
        });
    }
}
