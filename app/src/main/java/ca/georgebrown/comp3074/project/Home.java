package ca.georgebrown.comp3074.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.User.User;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView name = findViewById(R.id.txtName);
        User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        name.setText(validatedUser.getName());
    }
}
