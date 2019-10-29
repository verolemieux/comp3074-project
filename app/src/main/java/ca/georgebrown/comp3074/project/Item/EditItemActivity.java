package ca.georgebrown.comp3074.project.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        final TextView itemName = findViewById(R.id.txtItemName);
        Button btnSave = findViewById(R.id.btnSave);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        final Item editItem = (Item) getIntent().getSerializableExtra("Item");
        final ItemAdapter adapter = (ItemAdapter) getIntent().getSerializableExtra("Adapter");
        itemName.setText(editItem.getItem_Name());


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem.setItem_Name(itemName.getText().toString());
                if(!validatedUser.Item_List.isEmpty())
                {for(Item i : validatedUser.Item_List)
                {
                    if(i.getItem_Id() == editItem.getItem_Id())
                    {
                        i.setItem_Name(editItem.getItem_Name());
                    }
                }}
                Intent itemIntent = new Intent(v.getContext(), ItemsActivity.class);
                validatedUser.Item_List.add(new Item(4, "Mango"));
                itemIntent.putExtra("ValidatedUser", validatedUser);
                adapter.notifyDataSetChanged();
                itemIntent.putExtra("Adapter", adapter);
                startActivity(itemIntent);
            }
        });
    }
}