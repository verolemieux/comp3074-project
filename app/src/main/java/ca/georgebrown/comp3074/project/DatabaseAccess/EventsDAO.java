package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.Context;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.Event.Event;

public class EventsDAO {
    private DBHelper dbHelper;
    private ArrayList<Event> events = new ArrayList<>();
    public EventsDAO(Context context){
        dbHelper = new DBHelper(context);

    }
}
