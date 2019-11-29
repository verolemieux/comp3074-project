package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.provider.CalendarContract;

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

    public ArrayList<Event> getAllEvents(String email){
        Cursor c = getAllEventsHelper(email);
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
    private Cursor getAllEventsHelper(String email){
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
        String selection = EventsContract.EventsEntity.COLUMN_NAME_USER_EMAIL_EVENTS+"=?";
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
