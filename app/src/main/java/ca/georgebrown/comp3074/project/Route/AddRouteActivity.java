package ca.georgebrown.comp3074.project.Route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.RoutesDAO;
import ca.georgebrown.comp3074.project.R;

public class AddRouteActivity extends BaseActivity {

    TextView routeNameTxt;
    TextView routeOriginTxt;
    TextView routeDestinationTxt;
    ImageButton openRouteOriginBtn;
    ImageButton openRouteDestinationBtn;
    Button openRouteBtn;
    Button addBtn;
    RoutesDAO routeTable;
    Route newR = new Route(1, "", 0, 0, 0, "1", "1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_add_route, null, false);
        drawer.addView(contentView, 0);

        routeNameTxt = findViewById(R.id.txtRouteName);
        routeOriginTxt = findViewById(R.id.txtRouteFrom);
        routeDestinationTxt = findViewById(R.id.txtRouteTo);
        openRouteOriginBtn = findViewById(R.id.btnOpenOrigin);
        openRouteDestinationBtn = findViewById(R.id.btnOpenDest);
        openRouteBtn = findViewById(R.id.btnOpenRoute);
        addBtn = findViewById(R.id.btnAdd);

        routeTable = new RoutesDAO(this);

        openRouteOriginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMaps = new Intent(view.getContext(), MapsActivity.class);
                openMaps.putExtra("ValidatedUser", validatedUser);
                String originAddress = routeOriginTxt.getText().toString();
                openMaps.putExtra("address", originAddress);
                startActivity(openMaps);
            }
        });
        openRouteDestinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMaps = new Intent(view.getContext(), MapsActivity.class);
                openMaps.putExtra("ValidatedUser", validatedUser);
                String destAddress = routeDestinationTxt.getText().toString();
                openMaps.putExtra("address", destAddress);
                startActivity(openMaps);
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
}
