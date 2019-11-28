package ca.georgebrown.comp3074.project.Route;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class RoutesActivity extends BaseActivity
    implements FetchAddressTask.OnTaskCompleted{

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient locationProviderClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_routes, null, false);
        drawer.addView(contentView, 0);

        if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            //NO PERMISSION YET
            ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d("PERMISSION", "Permissions granted");
        }

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //do something
                }
            }
        });

        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                Location location = locationResult.getLastLocation();
                if (location != null){
                    //do something
                    new FetchAddressTask(RoutesActivity.this, RoutesActivity.this).execute(location);
                }
            }
        };

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
                startActivityForResult(addRoute, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode == 1)
        {
            //route_list = routesTable.getRoutes(validatedUser.getemail());
            //adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_LOCATION_PERMISSION){
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startTracking(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); //every 10 secs
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, null
        );
    }

    private void stopTracking(){
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume(){
        startTracking();
        super.onResume();
    }

    @Override
    protected void onPause(){
        stopTracking();
        super.onPause();
    }

    @Override
    public void onTaskCompleted(String result) {
//        TextView address = findViewById(R.id.textAddress);
//        address.setText(result);
        //do something
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
