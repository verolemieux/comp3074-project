package ca.georgebrown.comp3074.project.Event;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.BPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.EventsContract;
import ca.georgebrown.comp3074.project.DatabaseAccess.EventsDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.RoutesDAO;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.Route.Route;
import ca.georgebrown.comp3074.project.User.User;

public class EditEventActivity extends BaseActivity {
    EventsDAO eventsDAO;
    User validatedUser;
    Event selected_event;
    EditText event_name;
    EditText event_date;
    EditText event_desc;
    BPDAO bpdao;
    RoutesDAO routesDAO;
    Button save_btn;
    Button delete_btn;
    Backpack selected_bp;
    Route selected_route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_edit_event, null, false);
        drawer.addView(contentView, 0);

        routesDAO = new RoutesDAO(this);
        bpdao = new BPDAO(this);
        eventsDAO = new EventsDAO(this);
        save_btn = findViewById(R.id.btnSave);
        delete_btn = findViewById(R.id.btnDelete);
        final Spinner backpack_spinner = (Spinner) findViewById(R.id.backpack_spinner2);
        final Spinner route_spinner = (Spinner) findViewById(R.id.route_spinner2);
        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        selected_event = (Event)getIntent().getSerializableExtra("Event");
        event_name = findViewById(R.id.txtEventName);
        event_name.setText(selected_event.getEvent_Name());
        event_date = findViewById(R.id.txtEventDate);
        event_date.setText(selected_event.getDate());
        event_desc = findViewById(R.id.txtEventDescription);
        event_desc.setText(selected_event.getDescription());

        final ArrayList<Backpack> backpacks = bpdao.getAllBP(validatedUser.getEmail(), "");
        final ArrayList<Route> routes = routesDAO.getRouteList(validatedUser.getEmail(), "");
        final BackpackSpinAdapter Backpack_adapter;
        final RouteSpinAdapter route_adapter;
        Backpack_adapter = new BackpackSpinAdapter(this,R.layout.backpack_row_layout,backpacks);
        backpack_spinner.setAdapter(Backpack_adapter);
        route_adapter = new RouteSpinAdapter(this,R.layout.route_row_layout,routes);
        route_spinner.setAdapter(route_adapter);

        long bp_id = selected_event.getBackpack();
        long route_id = selected_event.getRoute();

        if(bp_id != -1){
            backpack_spinner.setSelection((int)bp_id-1);
        }
        if(route_id != -1){
            route_spinner.setSelection((int)route_id-1);
        }
        /*selected_bp = (Backpack) backpack_spinner.getItemAtPosition(0);
        selected_route = (Route) route_spinner.getItemAtPosition(0);
        selected_bp.setBackpack_Id(eventsDAO.getBPId(selected_event.getEvent_Id(), validatedUser.getEmail(), ""));
        selected_route.setRoute_Id((int)eventsDAO.getRouteId(selected_event.getEvent_Id(), validatedUser.getEmail(), ""));

        if(selected_bp.getBackpack_Id() != -1){
            selected_bp = bpdao.getBackpackById(selected_bp.getBackpack_Id(), validatedUser.getEmail());
            backpack_spinner.setSelection((int)selected_bp.getBackpack_Id()-1);
        }
        if(selected_route.getRoute_Id() != -1){
            selected_route = routesDAO.getRouteById(selected_route.getRoute_Id(), validatedUser.getEmail(), "");
            route_spinner.setSelection(selected_route.getRoute_Id()-1);
        }*/



        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(event_name.getText().toString().equals("")){
                    Toast.makeText(EditEventActivity.this, "Invalid event name",Toast.LENGTH_LONG ).show();
                }
                else if(event_date.getText().toString().equals("")){
                    Toast.makeText(EditEventActivity.this, "Invalid event date",Toast.LENGTH_LONG ).show();;
                }
                else if(event_desc.getText().toString().equals("")){
                    Toast.makeText(EditEventActivity.this, "Invalid event description",Toast.LENGTH_LONG ).show();;
                }
                else if(selected_bp == null || selected_route == null){
                    Toast.makeText(EditEventActivity.this, "Please select an backpack a backpack and route", Toast.LENGTH_LONG).show();
                }
                else{
                    selected_event.setEvent_Name(event_name.getText().toString());
                    selected_event.setDate(event_date.getText().toString());
                    selected_event.setDescription(event_desc.getText().toString());
                    selected_event.setBackpack(selected_bp.getBackpack_Id());
                    selected_event.setRoute(selected_route.getRoute_Id());
                    eventsDAO.updateEvent(selected_event, validatedUser.getEmail());
                    Intent returnIntent = new Intent(view.getContext(), EventsContract.EventsEntity.class);
                    returnIntent.putExtra("ValidatedUser", validatedUser);
                    setResult(2, returnIntent);
                    finish();
                }
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsDAO.deleteEvent(selected_event, validatedUser.getEmail());
                Intent returnIntent = new Intent(view.getContext(), EventsContract.EventsEntity.class);
                returnIntent.putExtra("ValidatedUser", validatedUser);
                setResult(2, returnIntent);
                finish();
            }
        });

        backpack_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                long bp_id = selected_event.getBackpack();
                backpack_spinner.setSelection((i));
                selected_bp = (Backpack) adapterView.getItemAtPosition(i);
                selected_event.setBackpack(bp_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(selected_bp.getBackpack_Id() == -1) {
                    backpack_spinner.setSelection((int) selected_bp.getBackpack_Id() - 1);
                }
            }
        });
        route_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                long route_id = selected_event.getRoute();
                route_spinner.setSelection((i));
                selected_route = (Route) adapterView.getItemAtPosition(i);
                selected_event.setRoute(route_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(selected_route.getRoute_Id() == -1) {
                    route_spinner.setSelection(selected_route.getRoute_Id() - 1);
                }
            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
