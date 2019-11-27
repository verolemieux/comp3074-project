package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.Event.Event;

public class EventsDAO {
    private DBHelper dbHelper;
    private ArrayList<Event> events = new ArrayList<>();
    public EventsDAO(Context context){
        dbHelper = new DBHelper(context);
        dbHelper.getWritableDatabase().execSQL(EventsContract.EventsEntity.SQL_CREATE_EVENTS);
    }
    public ArrayList<Event> getAllEvents(String email){
        Cursor c = getAllEventsHelper(email);
        while(c.moveToNext()){
            Event event = new Event(
                    c.getInt(c.getColumnIndexOrThrow(EventsContract.EventsEntity._ID)),
                    c.getString(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_NAME)),
                    c.getString(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DATE)),
                    c.getString(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_DESCRIPTION)),
                    c.getInt(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_BACKPACK)),
                    c.getInt(c.getColumnIndexOrThrow(EventsContract.EventsEntity.COLUMN_NAME_EVENT_ROUTE))
            );
            events.add(event);
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
                EventsContract.EventsEntity.COLUMN_NAME_EVENT_ROUTE
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
