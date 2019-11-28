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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.Route.Route;
import ca.georgebrown.comp3074.project.User.User;

public class AddEventActivity extends BaseActivity {
    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_add_event, null, false);
        drawer.addView(contentView, 0);

        //POPULATING SPINNERS
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        final ArrayList<Backpack> backpacks = validatedUser.Backpack_List;
        final ArrayList<Route> routes = validatedUser.Route_List;
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
                    int lastId = events.get(events.size()-1).getEvent_Id();
                    lastId++;
                    Backpack backpack = validatedUser.getBackpack(backpack_spinner.getSelectedItem().toString());
                    Route route = validatedUser.getRoute(route_spinner.getSelectedItem().toString());
                    Event event = new Event(lastId,event_name.getText().toString(),event_date.getText().toString(),event_desc.getText().toString(),backpack.getBackpack_Id(),route.getRoute_Id());
                    validatedUser.Event_List.add(event);
                    Intent return_event = new Intent(view.getContext(), EventsActivity.class);
                    return_event.putExtra("ValidatedUser", validatedUser);
                    startActivity(return_event);
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
