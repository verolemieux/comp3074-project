package ca.georgebrown.comp3074.project.Backpack;

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
import android.widget.ListView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.Item.ItemAdapter;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class AddBackpackActivity extends BaseActivity {

    ListView itemlist;
    Button btnAdd;
    User validatedUser;
    ArrayList<Backpack> userBackpacks;
    ArrayList<Item> items;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_add_backpack, null, false);
        drawer.addView(contentView, 0);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
