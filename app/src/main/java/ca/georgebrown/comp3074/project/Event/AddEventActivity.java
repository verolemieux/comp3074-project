package ca.georgebrown.comp3074.project.Event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.Route.Route;
import ca.georgebrown.comp3074.project.User.User;

public class AddEventActivity extends AppCompatActivity {
    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

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
                    Event event = new Event(lastId,event_name.getText().toString(),event_date.getText().toString(),event_desc.getText().toString(),backpack,route);
                    validatedUser.Event_List.add(event);
                    for(Event e : validatedUser.Event_List){
                        System.out.println(e.getEvent_Name());
                    }
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
        //
    }
}
