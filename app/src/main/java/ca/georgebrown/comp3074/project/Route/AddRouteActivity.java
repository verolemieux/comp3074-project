package ca.georgebrown.comp3074.project.Route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.RoutesDAO;
import ca.georgebrown.comp3074.project.R;

public class AddRouteActivity extends BaseActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationManager locationManager;
    private String locationProvider;

    Geocoder geocoder;
    List<Address> addresses;

    EditText routeNameTxt;
    EditText routeOriginTxt;
    EditText routeDestinationTxt;
    ImageButton openRouteOriginBtn;
    ImageButton openRouteDestinationBtn;
    Button openRouteBtn;
    Button startBtn;
    Button stopBtn;
    Button addBtn;
    RoutesDAO routeTable;
    Route newR = new Route(1, "", 0, 0, 0, "1", "1");
    Spinner difficultySpinner;
    Spinner ratingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_add_route, null, false);
        drawer.addView(contentView, 0);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d("PERMISSION", "Permissions granted");
        }

        routeNameTxt = findViewById(R.id.txtRouteName);
        routeOriginTxt = findViewById(R.id.txtRouteFrom);
        routeDestinationTxt = findViewById(R.id.txtRouteTo);
        openRouteOriginBtn = findViewById(R.id.btnOpenOrigin);
        openRouteDestinationBtn = findViewById(R.id.btnOpenDest);
        openRouteBtn = findViewById(R.id.btnOpenRoute);
        startBtn = findViewById(R.id.btnStart);
        stopBtn = findViewById(R.id.btnStop);
        addBtn = findViewById(R.id.btnAdd);

        difficultySpinner = findViewById(R.id.spinnerDifficulty);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this, R.array.difficulty, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        difficultySpinner.setAdapter(difficultyAdapter);

        ratingSpinner = findViewById(R.id.spinnerRating);
        ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(this, R.array.rating, android.R.layout.simple_spinner_item);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ratingSpinner.setAdapter(ratingAdapter);

        routeTable = new RoutesDAO(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = LocationManager.NETWORK_PROVIDER;

        geocoder = new Geocoder(this, Locale.getDefault());

        openRouteOriginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMaps = new Intent(view.getContext(), MapsActivity.class);
                openMaps.putExtra("ValidatedUser", validatedUser);
                String originAddress = routeOriginTxt.getText().toString();
                openMaps.putExtra("address", originAddress);
                if (!originAddress.isEmpty()) startActivity(openMaps);
            }
        });

        openRouteDestinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMaps = new Intent(view.getContext(), MapsActivity.class);
                openMaps.putExtra("ValidatedUser", validatedUser);
                String destAddress = routeDestinationTxt.getText().toString();
                openMaps.putExtra("address", destAddress);
                if (!destAddress.isEmpty()) startActivity(openMaps);
            }
        });

        openRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMaps = new Intent(view.getContext(), MapsActivity.class);
                openMaps.putExtra("ValidatedUser", validatedUser);
                String originAddress = routeOriginTxt.getText().toString();
                openMaps.putExtra("origin", originAddress);
                String destAddress = routeDestinationTxt.getText().toString();
                openMaps.putExtra("destination", destAddress);

                //startActivity(openMaps);
                startActivityForResult(openMaps, 1);
                if (!originAddress.isEmpty() && !destAddress.isEmpty()) startActivity(openMaps);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startTracking();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    stopTracking();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRIntent = new Intent(v.getContext(), RoutesActivity.class);
                addRIntent.putExtra("validatedUser", validatedUser);
                String routeName = routeNameTxt.getText().toString();
                String originAddress = routeOriginTxt.getText().toString();
                String destinationAddress = routeDestinationTxt.getText().toString();
                newR.setRoute_Name(routeName);
                newR.setRoute_Start_Location(originAddress);
                newR.setRoute_End_Location(destinationAddress);
                routeTable.addRoute(newR, validatedUser);
                setResult(1, addRIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            Log.d("1", "1");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void startTracking() throws IOException {
        String currentLocationAddress = getLocationAddress(getCurrentLocation());
        routeOriginTxt.setText(currentLocationAddress);
    }

    private void stopTracking() throws IOException {
        String currentLocationAddress = getLocationAddress(getCurrentLocation());
        routeDestinationTxt.setText(currentLocationAddress);
    }

    private Location getCurrentLocation(){
        @SuppressLint("MissingPermission")
        Location currentLocation = locationManager.getLastKnownLocation(locationProvider);
        return currentLocation;
    }

    private String getLocationAddress(Location location) throws IOException {
        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        return address + ", " + city + ", " + state;
    }
}
