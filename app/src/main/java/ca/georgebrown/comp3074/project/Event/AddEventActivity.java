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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.DatabaseAccess.BPDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.EventsDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.RoutesDAO;
import ca.georgebrown.comp3074.project.Item.AddItemActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.Route.Route;
import ca.georgebrown.comp3074.project.User.User;

public class AddEventActivity extends BaseActivity {
    ArrayList<Event> events;
    BPDAO bpdao;
    RoutesDAO routesDAO;
    EventsDAO eventsDAO;
    Backpack selected_bp;
    Route selected_route;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_add_event, null, false);
        drawer.addView(contentView, 0);
        //INSTANTIATING
        bpdao = new BPDAO(this);
        routesDAO = new RoutesDAO(this);
        eventsDAO = new EventsDAO(this);

        //POPULATING SPINNERS
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        final ArrayList<Backpack> backpacks = bpdao.getAllBP(validatedUser.getEmail());
        final ArrayList<Route> routes = routesDAO.getRouteList(validatedUser.getEmail(), "");
        events = validatedUser.Event_List;
        final Spinner backpack_spinner = (Spinner) findViewById(R.id.backpack_spinner);
        final Spinner route_spinner = (Spinner) findViewById(R.id.route_spinner);
        final BackpackSpinAdapter Backpack_adapter;
        final RouteSpinAdapter route_adapter;
        Backpack_adapter = new BackpackSpinAdapter(this,R.layout.backpack_row_layout,backpacks);
        backpack_spinner.setAdapter(Backpack_adapter);
        route_adapter = new RouteSpinAdapter(this,R.layout.route_row_layout,routes);
        route_spinner.setAdapter(route_adapter);
        //
        // ADD BUTTON ON CLICK

        Button add_button = findViewById(R.id.btnAdd2);
        final EditText event_name = findViewById(R.id.event_name);
        final EditText event_date = findViewById(R.id.date_et);
        final EditText event_desc = findViewById(R.id.desc_et);
        final TextView error_label = findViewById(R.id.error_message);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!event_name.getText().toString().equals("")&&!event_date.getText().toString().equals("")&&!event_desc.getText().toString().equals("")){
                    Backpack backpack =  bpdao.getBackpackByName(selected_bp.getBackpack_Name(), validatedUser.getEmail());
                    Route route =  routesDAO.getRouteByName(selected_route.getRoute_Name(), validatedUser.getEmail(), "");
                    long lastId = eventsDAO.addEvent(event_name.getText().toString(),
                            event_date.getText().toString(),
                            event_desc.getText().toString(),
                            backpack.getBackpack_Id(),
                            route.getRoute_Id(),
                            validatedUser.getEmail()
                    );

                    Event event = new Event(lastId,event_name.getText().toString(),event_date.getText().toString(),event_desc.getText().toString(),backpack.getBackpack_Id(),route.getRoute_Id());
                    validatedUser.Event_List.add(event);
                    Intent return_event = new Intent(view.getContext(), EventsActivity.class);
                    return_event.putExtra("ValidatedUser", validatedUser);
                    return_event.putExtra("NewEvent", event);
                    setResult(1,return_event);
                    finish();
                }else {
                    if (event_name.getText().toString().equals("")) {
                        error_label.setText("Please add a name");
                    }
                    if (event_date.getText().toString().equals("")) {
                        error_label.setText("Please add a date");
                    }
                    if (event_desc.getText().toString().equals("")) {
                        error_label.setText("Please add a description");
                    }
                }
            }
        });

        backpack_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_bp = (Backpack) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        route_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_route = (Route) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
