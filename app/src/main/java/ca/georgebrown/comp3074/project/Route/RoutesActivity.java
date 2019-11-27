package ca.georgebrown.comp3074.project.Route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class RoutesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_routes, null, false);
        drawer.addView(contentView, 0);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
