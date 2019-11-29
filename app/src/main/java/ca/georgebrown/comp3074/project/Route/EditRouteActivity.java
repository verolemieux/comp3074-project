package ca.georgebrown.comp3074.project.Route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.RoutesDAO;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EditRouteActivity extends BaseActivity {

    TextView routeNameTxt;
    TextView routeOriginTxt;
    TextView routeDestinationTxt;
    ImageButton openRouteOriginBtn;
    ImageButton openRouteDestinationBtn;
    Button openRouteBtn;
    Button saveBtn;
    Button deleteBtn;
    Button shareBtn;
    RoutesDAO routeTable;
    User validatedUser;
    Route editRoute;

    Spinner difficultySpinner;
    Spinner ratingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_edit_route, null, false);
        drawer.addView(contentView, 0);
        routeTable = new RoutesDAO(this);
        routeNameTxt = findViewById(R.id.txtRouteName);
        routeOriginTxt = findViewById(R.id.txtRouteFrom);
        routeDestinationTxt = findViewById(R.id.txtRouteTo);
        openRouteOriginBtn = findViewById(R.id.btnOpenOrigin);
        openRouteDestinationBtn = findViewById(R.id.btnOpenDest);
        openRouteBtn = findViewById(R.id.btnOpenRoute);
        saveBtn = findViewById(R.id.btnSave);
        deleteBtn = findViewById(R.id.btnDelete);
        shareBtn = findViewById(R.id.btnShare);

        validatedUser = (User)getIntent().getSerializableExtra("validatedUser");
        editRoute = (Route)getIntent().getSerializableExtra("editRoute");

        Log.d("Dif Edit", String.valueOf(editRoute.getRoute_Difficulty()));
        Log.d("Rating Edit", String.valueOf(editRoute.getRoute_Rating()));

        routeNameTxt.setText(editRoute.getRoute_Name());
        routeOriginTxt.setText(editRoute.getRoute_Start_Location());
        routeDestinationTxt.setText(editRoute.getRoute_End_Location());

        difficultySpinner = findViewById(R.id.spinnerDifficulty);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this, R.array.difficulty, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setSelection(editRoute.getRoute_Difficulty()-1);

        ratingSpinner = findViewById(R.id.spinnerRating);
        ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(this, R.array.rating, android.R.layout.simple_spinner_item);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ratingSpinner.setAdapter(ratingAdapter);
        ratingSpinner.setSelection(editRoute.getRoute_Rating()-1);

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
                if (!originAddress.isEmpty() && !destAddress.isEmpty()) startActivity(openMaps);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent routeIntent = new Intent(v.getContext(), RoutesActivity.class);
                String routeName = routeNameTxt.getText().toString();
                String originAddress = routeOriginTxt.getText().toString();
                String destinationAddress = routeDestinationTxt.getText().toString();
                int rating = Integer.parseInt(ratingSpinner.getSelectedItem().toString());
                int difficulty = Integer.parseInt(difficultySpinner.getSelectedItem().toString());
                editRoute.setRoute_Name(routeName);
                editRoute.setRoute_Start_Location(originAddress);
                editRoute.setRoute_End_Location(destinationAddress);
                editRoute.setRoute_Difficulty(difficulty);
                editRoute.setRoute_Rating(rating);
                routeTable.editRoute(editRoute, validatedUser, editRoute.getRoute_Id());
                routeIntent.putExtra("validatedUser", validatedUser);
                setResult(2, routeIntent);
                finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent routeIntent = new Intent(v.getContext(), RoutesActivity.class);
                routeTable.deleteRoute(validatedUser, editRoute.getRoute_Id());
                routeIntent.putExtra("validatedUser", validatedUser);
                setResult(2, routeIntent);
                finish();
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
