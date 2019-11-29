package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.Event.Event;

public class EventsDAO {
    private DBHelper dbHelper;
    private ArrayList<Event> events = new ArrayList<>();
    public EventsDAO(Context context){
        dbHelper = new DBHelper(context);
        //dbHelper.getWritableDatabase().execSQL(EventsContract.EventsEntity.SQL_DROP_EVENTS_);
        //dbHelper.getWritableDatabase().execSQL(EventsContract.EventsEntity.SQL_CREATE_EVENTS);
    }
    public void updateEvent(Event event, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_NAME, event.getEvent_Name());
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DESCRIPTION, event.getDescription());
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DATE, event.getDate());
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_BACKPACK, event.getBackpack());
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_ROUTE, event.getRoute());
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS, email);
        db.update(EventsContract.EventsEntity.TABLE_NAME_EVENTS, cv, "_id="+event.getEvent_Id()+" and "+
                EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS+" = '"+email+"'", null);
    }
    public long getBPId(long eventId, String email, String key){
        ArrayList<Event> events = getAllEvents(email, key);
        for(int x = 0; x<events.size();x++){
            if(events.get(x).getEvent_Id() == eventId){
                return events.get(x).getBackpack();
            }
        }
        return -1;
    }
    public long getRouteId(long eventId, String email, String key){
        ArrayList<Event> events = getAllEvents(email, key);
        for(int x = 0; x<events.size();x++){
            if(events.get(x).getEvent_Id() == eventId){
                return events.get(x).getRoute();
            }
        }
        return -1;
    }
    public long addEvent(String name, String date, String desc, long bp_id, long r_id, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_NAME, name);
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DESCRIPTION, desc);
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DATE, date);
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_BACKPACK, bp_id);
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_EVENT_ROUTE, r_id);
        cv.put(EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS, email);
        return db.insert(EventsContract.EventsEntity.TABLE_NAME_EVENTS, null, cv);
    }
    public void deleteEvent(Event event, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(EventsContract.EventsEntity.TABLE_NAME_EVENTS, "_id="+event.getEvent_Id()+" AND "+
                EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS +" = '"+email+"'",null);
    }

    public ArrayList<Event> getAllEvents(String email, String key){
        Cursor c = getAllEventsHelper(email, key);
        while(c.moveToNext()){
            Event e = new Event(
                    c.getInt(c.getColumnIndexOrThrow(EventsContract.EventsEntity._ID)),
                    c.getString(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_NAME)),
                    c.getString(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DATE)),
                    c.getString(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DESCRIPTION)),
                    c.getInt(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_BACKPACK)),
                    c.getInt(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_ROUTE))
            );
            events.add(e);
        }
        c.close();
        return events;
    }
    private Cursor getAllEventsHelper(String email, String key){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                EventsContract.EventsEntity._ID,
                EventsContract.EventsEntity.COLUMN_NAME_EVENT_NAME,
                EventsContract.EventsEntity.COLUMN_NAME_EVENT_DATE,
                EventsContract.EventsEntity.COLUMN_NAME_EVENT_DESCRIPTION,
                EventsContract.EventsEntity.COLUMN_NAME_EVENT_BACKPACK,
                EventsContract.EventsEntity.COLUMN_NAME_EVENT_ROUTE,
                EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS
        };
        if(key!="") {
            String selection = EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS + "=? AND "+
                    EventsContract.EventsEntity.COLUMN_NAME_EVENT_NAME + " LIKE '%"+key+"%'";
            String[] selectionArgs = {email};

            return db.query(
                    EventsContract.EventsEntity.TABLE_NAME_EVENTS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
        }
        String selection = EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS + "=?";
        String[] selectionArgs = {email};

        return db.query(
                EventsContract.EventsEntity.TABLE_NAME_EVENTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }
}
