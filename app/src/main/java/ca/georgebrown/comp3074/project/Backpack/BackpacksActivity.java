package ca.georgebrown.comp3074.project.Backpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ca.georgebrown.comp3074.project.AboutActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class BackpacksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);

        ImageButton addBackpack = findViewById(R.id.btnAddBackpack);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");

        addBackpack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addBackpack = new Intent(v.getContext(), AddBackpackActivity.class);
//                addBackpack.putExtra("ValidatedUser", validatedUser);
                startActivity(addBackpack);
            }
        });
    }
}
