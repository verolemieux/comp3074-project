package ca.georgebrown.comp3074.project.Route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;

public class EditRouteActivity extends BaseActivity {

    TextView routeNameTxt;
    TextView routeOriginTxt;
    TextView routeDestinationTxt;
    ImageButton openRouteOriginBtn;
    ImageButton openRouteDestinationBtn;
    Button saveBtn;
    Button deleteBtn;
    Button shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_edit_route, null, false);
        drawer.addView(contentView, 0);

        routeNameTxt = findViewById(R.id.txtRouteName);
        routeOriginTxt = findViewById(R.id.txtRouteFrom);
        routeDestinationTxt = findViewById(R.id.txtRouteTo);
        openRouteOriginBtn = findViewById(R.id.btnOpenOrigin);
        openRouteDestinationBtn = findViewById(R.id.btnOpenDest);
        saveBtn = findViewById(R.id.btnAdd);
        deleteBtn = findViewById(R.id.btnDelete);
        shareBtn = findViewById(R.id.btnShare);

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

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String routeName = routeNameTxt.getText().toString();
                String originAddress = routeOriginTxt.getText().toString();
                String destinationAddress = routeDestinationTxt.getText().toString();
                //persist to database
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from database
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
