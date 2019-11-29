package ca.georgebrown.comp3074.project.Backpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.DatabaseAccess.BPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.Item.ItemAdapter;
import ca.georgebrown.comp3074.project.QRCode.QRCode;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EditBackpackActivity extends AppCompatActivity {
    BPDAO bpdao;
    ItemsDAO itemsDAO;
    TextView bp_name;
    ArrayList<Item> items;
    User validatedUser;
    ItemAdapter itemAdapter;
    ItemAdapter itemAdapter2;
    Button save_btn;
    Button delete_btn;
    Button edit_button;
    Backpack selected_bp;
    TextView error_msg;

    ArrayList<Item> items_added;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_backpack);

        bpdao = new BPDAO(this);
        itemsDAO = new ItemsDAO(this);
        error_msg = findViewById(R.id.error_editbp);
        bp_name = findViewById(R.id.txtBPName);
        save_btn = findViewById(R.id.saveBtn);
        delete_btn = findViewById(R.id.deleteBtn);

        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        selected_bp = (Backpack)getIntent().getSerializableExtra("BP");
        bp_name.setText(selected_bp.getBackpack_Name());
        items = itemsDAO.getItems(validatedUser.getEmail(), "");
        final ListView total_item_list = findViewById(R.id.item_list);
        final ListView selected_item_list = findViewById(R.id.selected_item_list);
        final ArrayList<Item> selected_items = itemsDAO.getBPItems(selected_bp.getBackpack_Id(),validatedUser.getEmail());
        itemAdapter = new ItemAdapter(this,R.layout.item_layout,items, "");
        itemAdapter2 = new ItemAdapter(this,R.layout.item_layout, selected_items, "");
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
                        selected_items.remove(x);
                    }
                }
                itemAdapter2.notifyDataSetChanged();
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent return_event = new Intent(v.getContext(), QRCode.class);
                return_event.putExtra("ValidatedUser", validatedUser);
                startActivity(return_event);
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsDAO.removeAllItemsFromBP(validatedUser.getEmail());
                ArrayList<Backpack> backpacks = bpdao.getAllBP(validatedUser.getEmail());
                boolean name_exists = false;
                for (int x = 0; x < backpacks.size(); x++) {
                    if (backpacks.get(x).getBackpack_Name().equals(bp_name.getText().toString())) {
                        name_exists = true;
                    }
                }
                if (bp_name.getText().toString().equals("")) {
                    error_msg.setText("Backpack name cannot be empty");
                } else if (name_exists) {
                    error_msg.setText("Backpack name already exists!");
                } else {
                    selected_bp.setBackpack_Name(bp_name.getText().toString());
                    bpdao.updateBP(selected_bp, validatedUser.getEmail());
                    for (int x = 0; x < selected_items.size(); x++) {
                        itemsDAO.addToBP(selected_items.get(x), selected_bp.getBackpack_Id(), validatedUser.getEmail());
                    }
                    Intent returnIntent = new Intent(view.getContext(), BackpacksActivity.class);
                    returnIntent.putExtra("ValidatedUser", validatedUser);
                    setResult(2, returnIntent);
                    finish();
                }
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpdao.deleteBP(selected_bp, validatedUser.getEmail());
                Intent returnIntent = new Intent(view.getContext(), BackpacksActivity.class);
                returnIntent.putExtra("ValidatedUser", validatedUser);
                setResult(2,returnIntent);
                finish();
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
}
