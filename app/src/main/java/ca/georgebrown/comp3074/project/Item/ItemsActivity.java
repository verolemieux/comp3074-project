package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        final ItemAdapter adapter = new ItemAdapter(this, R.layout.item_layout, items);
        itemList.setAdapter(adapter);
    }
}
