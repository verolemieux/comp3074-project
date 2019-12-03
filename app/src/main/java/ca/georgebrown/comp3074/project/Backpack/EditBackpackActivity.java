package ca.georgebrown.comp3074.project.Backpack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.BPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.ItemBPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.Item.ItemAdapter;
import ca.georgebrown.comp3074.project.QRCode.QRCode;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EditBackpackActivity extends BaseActivity {
    static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private IntentIntegrator qrScan;
    View v;
    AdapterView<?> adapterView;

    BPDAO bpdao;
    ItemsDAO itemsDAO;
    TextView bp_name;
    ArrayList<Item> items;
    ArrayList<Item> selected_items = new ArrayList<>();
    ArrayList<String> selected = new ArrayList<>();
    User validatedUser;
    ItemAdapter itemAdapter;
    ItemAdapter itemAdapter2;
    Button save_btn;
    Button delete_btn;
    Button add_btn;
    Backpack selected_bp;
    TextView error_msg;
    ItemBPDAO itemBPDAO;
    ArrayList<Item> items_added;
    View contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        contentView = inflater.inflate(R.layout.activity_edit_backpack, null, false);
        drawer.addView(contentView, 0);

        bpdao = new BPDAO(this);
        itemBPDAO = new ItemBPDAO(this);
        itemsDAO = new ItemsDAO(this);
        error_msg = findViewById(R.id.error_editbp);
        bp_name = findViewById(R.id.txtBPName);
        save_btn = findViewById(R.id.saveBtn);
        delete_btn = findViewById(R.id.deleteBtn);
        add_btn = findViewById(R.id.btn_AddQR);

        items_added = new ArrayList<>();
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        selected_bp = (Backpack)getIntent().getSerializableExtra("BP");
        bp_name.setText(selected_bp.getBackpack_Name());
        final String originText = bp_name.getText().toString();
        items = itemsDAO.getItems(validatedUser.getEmail(), "");
        final ListView total_item_list = findViewById(R.id.item_list);
        final ListView selected_item_list = findViewById(R.id.selected_item_list);
        final ArrayList<Item> selected_items = itemBPDAO.getItemsFromBP(selected_bp.getBackpack_Id(), validatedUser.getEmail());//itemsDAO.getBPItems(selected_bp.getBackpack_Id(),validatedUser.getEmail());
        itemAdapter = new ItemAdapter(this,R.layout.item_layout,items);
        itemAdapter2 = new ItemAdapter(this,R.layout.item_layout, selected_items);
        total_item_list.setAdapter(itemAdapter);
        selected_item_list.setAdapter(itemAdapter2);
        for(int i = 0; i<itemAdapter2.getCount();i++){
            View v = selected_item_list.getAdapter().getView(i,null,null);
            TextView tv = (TextView) v.findViewById(R.id.txtItemLayout);
            tv.setBackgroundColor(Color.GREEN);
            //TextView tv = (TextView) v.findViewById(i);
            //tv.setBackgroundColor(Color.GREEN);
            //TextView et = (TextView) v.findViewById(R.id.txtItemLayout);
            //v.setBackgroundColor(Color.GREEN);
            /*View tv = getViewByPosition(i,selected_item_list);
            tv.setBackgroundColor(Color.GREEN);*/
        }

        total_item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Item new_item = (Item) parent.getItemAtPosition(i);
                boolean exists = false;
                for(int x = 0; x<selected_items.size();x++){
                    if(new_item.getItem_Name().equals(selected_items.get(x).getItem_Name())){
                        exists = true;
                    }
                }
                if(exists){
                    Toast.makeText(EditBackpackActivity.this, "Item already exists in backpack", Toast.LENGTH_SHORT).show();
                }else {
                    selected_items.add(new_item);
                    itemAdapter2.notifyDataSetChanged();
                    items_added.add(new_item);
                }
            }
        });

        selected_item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Item delete_item = (Item)parent.getItemAtPosition(i);
                for(int x = 0; x<selected_items.size();x++){
                    if(selected_items.get(x).getItem_Name().equals(delete_item.getItem_Name())){
                        itemBPDAO.deleteItemToBP(selected_bp.getBackpack_Id(), selected_items.get(x).getItem_Id(), validatedUser.getEmail());
                        selected_items.remove(x);
                    }
                }
                itemAdapter2.notifyDataSetChanged();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsDAO.removeAllItemsFromBP(validatedUser.getEmail());
                ArrayList<Backpack> backpacks = bpdao.getAllBP(validatedUser.getEmail(), "");
                boolean name_exists = false;
                for (int x = 0; x < backpacks.size(); x++) {
                    if (backpacks.get(x).getBackpack_Name().equals(bp_name.getText().toString())) {
                        name_exists = true;
                    }
                }
                if (bp_name.getText().toString().equals("")) {
                    error_msg.setText("Backpack name cannot be empty");
                } else if (name_exists && !originText.equals(bp_name.getText().toString())) {
                    error_msg.setText("Backpack name already exists!");
                } else {
                    selected_bp.setBackpack_Name(bp_name.getText().toString());
                    bpdao.updateBP(selected_bp, validatedUser.getEmail());
                    for (int x = 0; x < items_added.size(); x++) {
                        itemBPDAO.addItemToBP(selected_bp.getBackpack_Id(),items_added.get(x).getItem_Id(),validatedUser.getEmail());
                        //itemsDAO.addToBP(selected_items.get(x), selected_bp.getBackpack_Id(), validatedUser.getEmail());
                    }
                    Intent returnIntent = new Intent(view.getContext(), BackpacksActivity.class);
                    returnIntent.putExtra("ValidatedUser", validatedUser);
                    setResult(2, returnIntent);
                    finish();
                }
                Intent returnIntent = new Intent(view.getContext(), BackpacksActivity.class);
                returnIntent.putExtra("ValidatedUser", validatedUser);
                setResult(2,returnIntent);
                finish();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpdao.deleteBP(selected_bp, validatedUser.getEmail());
                itemBPDAO.deleteBP(selected_bp.getBackpack_Id(), validatedUser.getEmail());
                Intent returnIntent = new Intent(view.getContext(), BackpacksActivity.class);
                returnIntent.putExtra("ValidatedUser", validatedUser);
                setResult(2,returnIntent);
                finish();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    scanCode();
                } else {
                    requestPermission();
                }
            }
        });
        //ArrayList<Item> selected_bp_items = selected_bp.getItem_List();
        /*for(int i  = 0; i<itemAdapter.getCount();i++){
            for(int j = 0; j<selected_bp_items.size();j++){
                if(itemAdapter.getItem(i).getItem_Name().equals(selected_bp_items.get(j).getItem_Name())){
                    item_list.getAdapter().getItemId(j);
                    View tv = getViewByPosition(j,item_list);
                    tv.setBackgroundColor(Color.GREEN);

                }
            }
        }*/



    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void scanCode(){
        qrScan = new IntentIntegrator(EditBackpackActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
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
                    Toast.makeText(adapterView.getContext(), textView.getText().toString() + " selected", Toast.LENGTH_LONG).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
