package ca.georgebrown.comp3074.project.Route;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ca.georgebrown.comp3074.project.Backpack.AddBackpackActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class RoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        ImageButton addRoute = findViewById(R.id.btnAddRoute);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");

        addRoute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addRoute = new Intent(v.getContext(), AddRouteActivity.class);
                addRoute.putExtra("ValidatedUser", validatedUser);
                startActivity(addRoute);
            }
        });
    }
}
