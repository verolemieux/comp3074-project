package ca.georgebrown.comp3074.project.Event;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.AddBackpackActivity;
import ca.georgebrown.comp3074.project.Home;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        ImageButton addEvent = findViewById(R.id.addEvent_btn);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        final ArrayList<Event> events = validatedUser.Event_List;
        ListView eventList = findViewById(R.id.event_list);
        final EventAdapter adapter;
        adapter = new EventAdapter(this,R.layout.event_layout,events);
        eventList.setAdapter(adapter);
        Button home_btn = findViewById(R.id.home_btn);


        addEvent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addEvent = new Intent(v.getContext(), AddEventActivity.class);
                addEvent.putExtra("ValidatedUser", validatedUser);
                startActivity(addEvent);
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent return_home = new Intent(view.getContext(), Home.class);
                return_home.putExtra("ValidatedUser", validatedUser);
                startActivity(return_home);
            }
        });
    }
}
