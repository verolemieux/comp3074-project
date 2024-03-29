package ca.georgebrown.comp3074.project.Backpack;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.BPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.ItemBPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.Item.ItemAdapter;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class AddBackpackActivity extends BaseActivity {

    private IntentIntegrator qrScan;

    ListView itemlist;
    Button btnAdd;
    User validatedUser;
    ArrayList<Backpack> userBackpacks;
    ArrayList<Item> items;
    ItemAdapter adapter;
    ItemBPDAO itemBPDAO;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    TextView error_msg;
    ItemsDAO itemsDAO;
    Button addItem;
    BPDAO bpdao;
    ArrayList<String> selected = new ArrayList<>();
    ArrayList<Item> selected_items = new ArrayList<>();
    TextView items_added;
    View v;
    AdapterView<?> adapterView;
    View contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        contentView = inflater.inflate(R.layout.activity_add_backpack, null, false);
        drawer.addView(contentView, 0);
        Toast.makeText(this.getApplicationContext(),"Long press an item to add it to the backpack" , Toast.LENGTH_LONG).show();

        v = contentView;
        itemBPDAO = new ItemBPDAO(this);
        bpdao = new BPDAO(this);
        itemsDAO = new ItemsDAO(this);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        userBackpacks = (ArrayList<Backpack>)getIntent().getSerializableExtra("UserBackpacks");
        validatedUser.Item_List = itemsDAO.getItems(validatedUser.getEmail(), "");
        items = validatedUser.Item_List;
        addItem = findViewById(R.id.btnAddItem);
        error_msg = findViewById(R.id.error_tv_addbp);
        items_added = findViewById(R.id.items_added_tv);
        final TextView bp_name = findViewById(R.id.bp_name);
        btnAdd = findViewById(R.id.btnAdd);
        itemlist = findViewById(R.id.listItems);
        adapter = new ItemAdapter(this, R.layout.item_layout, items);
        adapterView = itemlist;
        itemlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemlist.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                boolean name_exists =  false;
                userBackpacks = bpdao.getAllBP(validatedUser.getEmail(), "");
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
                        itemBPDAO.addItemToBP(bpId,selected_items.get(x).getItem_Id(),validatedUser.getEmail());
                    }
                    addBackpackIntent.putExtra("NewBP", bp);
                    setResult(1, addBackpackIntent);
                    finish();
                }
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    scanCode();
                } else {
                    requestPermission();
                }
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
                adapter = new ItemAdapter(this, R.layout.item_layout, items);
                itemlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                itemlist.setAdapter(adapter);

            }
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null){

                /*
                if(result.getContents() == null) {
                    Log.d("MainActivity", "Cancelled scan");
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("MainActivity", "Scanned");
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    Log.d("Results", result.getContents());
                    int position = -1;
                    TextView textView = null;
                    for (int x = 0; x < items.size(); x++)
                    {
                        if(items.get(x).getItem_Name().equals(result.getContents()))
                        {
                            View v = itemlist.getAdapter().getView(x,null,null);
                            textView = (TextView) v.findViewById(R.id.txtItemLayout);
                            textView.setBackgroundColor(Color.GREEN);
                            position = x;
                            Log.d("item found", "a");
                        }
                    }
                    if(textView != null) {
                        error_msg.setText("");
                        boolean repeated_item = false;
                        for (int x = 0; x < selected_items.size(); x++) {
                            if (selected_items.get(x).getItem_Name().equals(textView.getText().toString())) {
                                repeated_item = true;
                            }
                        }
                        if (repeated_item) {
                            error_msg.setText("You have already added this item");
                        } else {
                            selected.add(textView.getText().toString());
                            textView.setBackgroundColor(Color.GREEN);
                            Item selected_item = (Item) adapterView.getItemAtPosition(position);
                            selected_items.add(selected_item);
                            items_added.setText(selected.size() + "");
                            Toast.makeText(adapterView.getContext(), textView.getText().toString() + " selected", Toast.LENGTH_LONG).show();
                        }
                    }*/
                    if(result.getContents() == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("MainActivity", "Scanned");
                        Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        Log.d("Results", result.getContents());
                        int position = -1;
                        for (int x = 0; x < items.size(); x++)
                        {
                            if(items.get(x).getItem_Name().equals(result.getContents()))
                            {
                                position = x;
                                Log.d("item found", "a");
                            }
                        }

                        error_msg.setText("");
                        //v = adapter.getView(position, null, (ViewGroup) contentView);
                        TextView textView = (TextView) v.findViewById(R.id.txtItemLayout);
                        boolean repeated_item = false;
                        for(int x = 0; x<selected_items.size();x++){
                            if(selected_items.get(x).getItem_Name().equals(textView.getText().toString())){
                                repeated_item = true;
                            }
                        }
                        if(repeated_item){
                            error_msg.setText("You have already added this item");
                        }
                        else {
                            textView.setBackgroundColor(Color.GREEN);
                            selected.add(textView.getText().toString());
                            Item selected_item = (Item) adapterView.getItemAtPosition(position);
                            selected_items.add(selected_item);
                            items_added.setText(selected.size() + "");
                            Toast.makeText(adapterView.getContext(), textView.getText().toString() + " selected", Toast.LENGTH_LONG).show();
                        }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void scanCode(){
        qrScan = new IntentIntegrator(AddBackpackActivity.this);
        qrScan.setBeepEnabled(true);
        qrScan.setOrientationLocked(true);
        qrScan.setPrompt("Scan a QR Code");
        qrScan.initiateScan();
    }

    public boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    scanCode();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}