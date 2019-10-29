package ca.georgebrown.comp3074.project.Event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ca.georgebrown.comp3074.project.Backpack.AddBackpackActivity;
import ca.georgebrown.comp3074.project.R;

public class EventsActivity extends AppCompatActivity {

    ImageButton addEvent = findViewById(R.id.btnAddEvent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        addEvent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent addEvent = new Intent(v.getContext(), AddEventActivity.class);
                startActivity(addEvent);
            }
        });
    }
}
