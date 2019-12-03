package ca.georgebrown.comp3074.project.Event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

import ca.georgebrown.comp3074.project.Backpack.EditBackpackActivity;

import ca.georgebrown.comp3074.project.DatabaseAccess.EventsDAO;
import ca.georgebrown.comp3074.project.HomeActivity;
import ca.georgebrown.comp3074.project.BaseActivity;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.User.User;

public class EventsActivity extends BaseActivity {
    EventsDAO eventsDAO;
    ArrayList<Event> userEvents;
    EventAdapter adapter;
    EditText txtEventName;

    EventsDAO eventTable;
    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_event, null, false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addView(contentView, 0);


        eventsDAO = new EventsDAO(this);
        txtEventName = findViewById(R.id.txtSearchEvents);
        ImageButton addEvent = findViewById(R.id.addEvent_btn);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        userEvents = eventsDAO.getAllEvents(validatedUser.getEmail(), "");
        validatedUser.Event_List = userEvents;
        ListView eventList = findViewById(R.id.event_list);
        adapter = new EventAdapter(this,R.layout.event_layout,userEvents);

        eventList.setAdapter(adapter);

        addEvent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addEvent = new Intent(v.getContext(), AddEventActivity.class);
                addEvent.putExtra("ValidatedUser", validatedUser);
                addEvent.putExtra("UserEvents", validatedUser.Event_List);
                startActivityForResult(addEvent,1);
            }
        });

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent editEvent = new Intent(view.getContext(), EditEventActivity.class);
                editEvent.putExtra("ValidatedUser", validatedUser);
                Event event = (Event) adapterView.getItemAtPosition(i);
                editEvent.putExtra("Event", event);
                startActivityForResult(editEvent, 2);
            }
        });

        txtEventName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userEvents = eventsDAO.getAllEvents(validatedUser.getEmail(), txtEventName.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (data != null) {
                Event event = (Event) data.getSerializableExtra("NewEvent");
                userEvents.add(event);
                adapter.notifyDataSetChanged();
            }
        }
        if(requestCode == 2){
            adapter.clear();
            userEvents = eventsDAO.getAllEvents(validatedUser.getEmail(), "");
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
