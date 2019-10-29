package ca.georgebrown.comp3074.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.DatabaseAccess.UserDBAccess;
import ca.georgebrown.comp3074.project.Item.ItemsActivity;
import ca.georgebrown.comp3074.project.User.User;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView name = findViewById(R.id.txtName);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        name.setText(validatedUser.getName());
        Button btnBackpack = findViewById(R.id.btnBackpacks);
        Button btnEvent = findViewById(R.id.btnEvents);
        Button btnRoute = findViewById(R.id.btnRoutes);
        Button btnItems = findViewById(R.id.btnItems);

        btnItems.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                    Intent itemIntent = new Intent(v.getContext(), ItemsActivity.class);
                    itemIntent.putExtra("ValidatedUser", validatedUser);
                    startActivity(itemIntent);
            }
        });
    }
}
