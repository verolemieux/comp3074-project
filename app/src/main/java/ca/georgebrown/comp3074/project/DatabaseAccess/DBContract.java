package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

import java.sql.Blob;

public class DBContract {

    private DBContract(){}

    public static class DBEntity implements BaseColumns{

        //################
        // USERS



        //################
        // BACKPACKS

        public static final String TABLE_NAME_BACKPACK = "backpacks";
        public static final String COLUMN_NAME_BACKPACK_NAME = "backpack_name";
        public static final String COLUMN_NAME_NUMBER_ITEMS = "number_of_items";
        public static final String COLUMN_NAME_BACKPACK_CREATION_DATE = "backpack_creation_date";
        public static final String COLUMN_NAME_USER_EMAIL_BACKPACKS = "email";

        public static final String SQL_CREATE_BACKPACK = "CREATE TABLE " + TABLE_NAME_BACKPACK + " ( " + _ID +
                " INTEGER PRIMARY KEY, " +COLUMN_NAME_BACKPACK_NAME + " TEXT, " + COLUMN_NAME_NUMBER_ITEMS
                + " INTEGER, " + COLUMN_NAME_BACKPACK_CREATION_DATE + " TEXT, " +
            COLUMN_NAME_USER_EMAIL_BACKPACKS + " TEXT)";

        public static final String SQL_DROP_BACKPACKS_ ="DROP TABLE IF EXISTS " + TABLE_NAME_BACKPACK;

        //################
        // ITEMS + BACKPACKS

        public static final String TABLE_NAME_ITEM_BACKPACK = "item_backpack";
        public static final String COLUMN_NAME_BACKPACK_ID = "backpack_id";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";

        public static final String SQL_CREATE_ITEM_BACKPACK = "CREATE TABLE " + TABLE_NAME_ITEM_BACKPACK +
                " ( " + COLUMN_NAME_BACKPACK_ID + " INTEGER, " + COLUMN_NAME_ITEM_ID +
                " INTEGER)";

        public static final String SQL_DROP_ITEM_BACKPACK_ ="DROP TABLE IF EXISTS " + TABLE_NAME_ITEM_BACKPACK;

        //################
        // EVENTS

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
