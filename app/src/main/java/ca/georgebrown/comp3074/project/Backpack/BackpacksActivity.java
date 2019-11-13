package ca.georgebrown.comp3074.project.Backpack;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class BackpacksActivity extends BaseActivity {

    User validatedUser;
    ImageButton addBackpack;
    ListView backpackList;
    ArrayList<Backpack> userBackpacks;
    BackpackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_backpack, null, false);
        drawer.addView(contentView, 0);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addBackpack = findViewById(R.id.btnAddBackpack);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        userBackpacks = validatedUser.getBackpack_List();
        backpackList = findViewById(R.id.listBackpack);
        adapter = new BackpackAdapter(this, R.layout.backpack_layout, userBackpacks);
        backpackList.setAdapter(adapter);
                addBackpack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addBackpack = new Intent(v.getContext(), AddBackpackActivity.class);
                addBackpack.putExtra("ValidatedUser", validatedUser);
                addBackpack.putExtra("UserBackpacks", validatedUser.Backpack_List);
                startActivityForResult(addBackpack, 1);
            }
        });
        addBackpack = findViewById(R.id.btnAddBackpack);
        addBackpack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addBackpack = new Intent(v.getContext(), AddBackpackActivity.class);
                addBackpack.putExtra("ValidatedUser", validatedUser);
                startActivityForResult(addBackpack, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
            userBackpacks = (ArrayList<Backpack>)getIntent().getSerializableExtra("UserBackpacks");
            backpackList = findViewById(R.id.listBackpack);
            adapter = new BackpackAdapter(this, R.layout.backpack_layout, userBackpacks);
            backpackList.setAdapter(adapter);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
