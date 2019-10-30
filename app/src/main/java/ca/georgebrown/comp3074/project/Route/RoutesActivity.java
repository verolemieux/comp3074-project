package ca.georgebrown.comp3074.project.Route;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

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
        final ArrayList<Route> routes = validatedUser.Route_List;
        ListView route_list = findViewById(R.id.route_list);
        final RouteAdapter adapter;
        adapter = new RouteAdapter(this,R.layout.route_layout,routes);
        route_list.setAdapter(adapter);
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
