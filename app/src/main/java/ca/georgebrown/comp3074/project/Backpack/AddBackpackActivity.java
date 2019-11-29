package ca.georgebrown.comp3074.project.Backpack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.BPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.Item.AddItemActivity;
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

    ItemsDAO itemsDAO;
    Button addItem;
    BPDAO bpdao;
    ArrayList<String> selected = new ArrayList<>();
    ArrayList<Item> selected_items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_add_backpack, null, false);
        drawer.addView(contentView, 0);

        Toast.makeText(this.getApplicationContext(),"Long press an item to add it to the backpack" , Toast.LENGTH_LONG).show();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bpdao = new BPDAO(this);
        itemsDAO = new ItemsDAO(this);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        userBackpacks = (ArrayList<Backpack>)getIntent().getSerializableExtra("UserBackpacks");
        validatedUser.Item_List = itemsDAO.getItems(validatedUser.getEmail(), "");
        items = validatedUser.Item_List;
        addItem = findViewById(R.id.btnAddItem);
        final TextView error_msg = findViewById(R.id.error_tv_addbp);
        final TextView items_added = findViewById(R.id.items_added_tv);
        final TextView bp_name = findViewById(R.id.bp_name);
        btnAdd = findViewById(R.id.btnAdd);
        itemlist = findViewById(R.id.listItems);
        adapter = new ItemAdapter(this, R.layout.item_layout, items, "");
        itemlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemlist.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                boolean name_exists =  false;
                userBackpacks = bpdao.getAllBP(validatedUser.getEmail());
                for(int i = 0; i<userBackpacks.size();i++){
                    if(userBackpacks.get(i).getBackpack_Name().equals(bp_name.getText().toString())){
                        name_exists = true;
                    }
                }
                if(name_exists){
                    error_msg.setText("Backpack name already exists!");
                }
                else if(selected.size() == 0){
                    error_msg.setText("Please select items to add");
                }else if(bp_name.getText().toString().equals("")){
                    error_msg.setText("Please enter a valid backpack name");
                }
                else {
                    long bpId = bpdao.addBackpack(bp_name.getText().toString(), selected.size(), validatedUser.getEmail());
                    Backpack bp = new Backpack(bpId,bp_name.getText().toString(), validatedUser.getEmail());
                    Intent addBackpackIntent = new Intent(v.getContext(), BackpacksActivity.class);
                    bp.setItem_List(selected_items);
                    for(int x = 0; x<selected_items.size();x++){
                        itemsDAO.addToBP(selected_items.get(x), bpId, validatedUser.getEmail());
                    }
                    //addBackpackIntent.putExtra("Items_Selected", selected_items);
                    addBackpackIntent.putExtra("NewBP", bp);
                    setResult(1, addBackpackIntent);
                    finish();
                }
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemIntent = new Intent(view.getContext(), AddItemActivity.class);
                addItemIntent.putExtra("ValidatedUser", validatedUser);
                startActivityForResult(addItemIntent,1);
                finish();
            }
        });
        itemlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                error_msg.setText("");
                TextView textView = (TextView) view.findViewById(R.id.txtItemLayout);
                boolean repeated_item = false;
                for(int x = 0; x<selected_items.size();x++){
                    if(selected_items.get(x).getItem_Name().equals(textView.getText().toString())){
                        repeated_item = true;
                    }
                }
                if(repeated_item){
                    error_msg.setText("You have already added this item");
                    return false;
                }
                else {
                    textView.setBackgroundColor(Color.GREEN);
                    selected.add(textView.getText().toString());
                    Item selected_item = (Item) adapterView.getItemAtPosition(i);
                    selected_items.add(selected_item);
                    items_added.setText(selected.size() + "");
                    Toast.makeText(adapterView.getContext(), textView.getText().toString() + " selected", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == RESULT_OK){
                validatedUser.Item_List = itemsDAO.getItems(validatedUser.getEmail(), "");
                items = validatedUser.Item_List;
                adapter = new ItemAdapter(this, R.layout.item_layout, items, "");
                itemlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                itemlist.setAdapter(adapter);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
