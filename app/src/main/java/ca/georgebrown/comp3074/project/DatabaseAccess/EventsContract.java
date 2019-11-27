package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

public class EventsContract {
    private EventsContract(){}
    public class EventsEntity implements BaseColumns{
        public static final String TABLE_NAME_EVENTS = "events";
        public static final String COLUMN_NAME_EVENT_NAME = "event_name";
        public static final String COLUMN_NAME_EVENT_DESCRIPTION = "event_desc";
        public static final String COLUMN_NAME_EVENT_DATE = "event_date";
        public static final String COLUMN_NAME_EVENT_BACKPACK = "backpack_id";
        public static final String COLUMN_NAME_EVENT_ROUTE = "route_id";
        public static final String COLUMN_NAME_USER_EMAIL_EVENTS = "email";

        public static final String SQL_CREATE_EVENTS = "CREATE TABLE " + TABLE_NAME_EVENTS + " ( " + _ID +
                " INTEGER PRIMARY KEY, " + COLUMN_NAME_EVENT_NAME + " TEXT, " + COLUMN_NAME_EVENT_DESCRIPTION +
                " TEXT, " + COLUMN_NAME_EVENT_DATE + " TEXT, " + COLUMN_NAME_EVENT_BACKPACK + " INTEGER, " +
                COLUMN_NAME_EVENT_ROUTE + " INTEGER " +
                COLUMN_NAME_USER_EMAIL_EVENTS + " TEXT)";

        public static final String SQL_DROP_EVENTS_ ="DROP TABLE IF EXISTS " + TABLE_NAME_EVENTS;

    }
}
