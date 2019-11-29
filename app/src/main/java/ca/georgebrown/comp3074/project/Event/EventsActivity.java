package ca.georgebrown.comp3074.project.Event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

import ca.georgebrown.comp3074.project.DatabaseAccess.EventsDAO;
import ca.georgebrown.comp3074.project.HomeActivity;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EventsActivity extends BaseActivity {

    EventsDAO eventTable;
    ArrayList<Event> events;
    EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_event, null, false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addView(contentView, 0);
        eventTable = new EventsDAO(this);
        ImageButton addEvent = findViewById(R.id.addEvent_btn);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        //events = validatedUser.Event_List;
        events = eventTable.getAllEvents(validatedUser.getEmail());
        ListView eventList = findViewById(R.id.event_list);
        adapter = new EventAdapter(this,R.layout.event_layout,events);
        eventList.setAdapter(adapter);

        addEvent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addEvent = new Intent(v.getContext(), AddEventActivity.class);
                addEvent.putExtra("ValidatedUser", validatedUser);
                startActivityForResult(addEvent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            events = eventTable.getAllEvents(validatedUser.getEmail());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
