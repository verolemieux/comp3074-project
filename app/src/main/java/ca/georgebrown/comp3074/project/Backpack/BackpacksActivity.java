package ca.georgebrown.comp3074.project.Backpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ca.georgebrown.comp3074.project.AboutActivity;
import ca.georgebrown.comp3074.project.R;

public class BackpacksActivity extends AppCompatActivity {

    ImageButton addBackpack = findViewById(R.id.btnAddBackpack);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);

        addBackpack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addBackpack = new Intent(v.getContext(), AddBackpackActivity.class);
                startActivity(addBackpack);
            }
        });
    }
}
