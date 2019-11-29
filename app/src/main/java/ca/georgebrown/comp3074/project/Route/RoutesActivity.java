package ca.georgebrown.comp3074.project.Route;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.RoutesDAO;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class RoutesActivity extends BaseActivity {

    User validatedUser;
    ArrayList<Route> routes;
    RouteAdapter adapter;
    ListView route_list;
    RoutesDAO routeTable;
    TextView searchRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        routeTable = new RoutesDAO(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_routes, null, false);
        drawer.addView(contentView, 0);

        ImageButton addRoute = findViewById(R.id.btnAddRoute);

        searchRoute = findViewById(R.id.txtSearchRoutes);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        routes = routeTable.getRouteList(validatedUser.getEmail(), "");
        route_list = findViewById(R.id.route_list);
        adapter = new RouteAdapter(this,R.layout.route_layout,routes);
        route_list.setAdapter(adapter);
        addRoute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addRoute = new Intent(v.getContext(), AddRouteActivity.class);
                addRoute.putExtra("ValidatedUser", validatedUser);
                startActivityForResult(addRoute, 1);
            }
        });

        searchRoute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                routes = routeTable.getRouteList(validatedUser.getEmail(), searchRoute.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        route_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent editRouteIntent = new Intent(view.getContext(), EditRouteActivity.class);
                editRouteIntent.putExtra("validatedUser", validatedUser);
                Route editRoute = (Route)parent.getItemAtPosition(position);
                editRouteIntent.putExtra("editRoute", editRoute);
                startActivityForResult(editRouteIntent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode == 1)
        {
            routes = routeTable.getRouteList(validatedUser.getEmail(), "");
            adapter.notifyDataSetChanged();
        }
        if(requestcode == 2)
        {
            routes = routeTable.getRouteList(validatedUser.getEmail(), "");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
