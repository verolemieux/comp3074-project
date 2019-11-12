package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

import java.sql.Blob;

public class DBContract {

    private DBContract(){}

    public static class DBEntity implements BaseColumns{

        //################
        // USERS

        public static final String TABLE_NAME_USERS = "users";
        public static final String COLUMN_NAME_USER_EMAIL = "email";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_ENROLLMENT_DATE = "enrollment_date";

        public static final String SQL_CREATE_USERS = "CREATE TABLE " + TABLE_NAME_USERS + " ( " + COLUMN_NAME_USER_EMAIL +
                " TEXT PRIMARY KEY, " +COLUMN_NAME_USER_NAME + " TEXT, " + COLUMN_NAME_PASSWORD
                + " TEXT, " + COLUMN_NAME_ENROLLMENT_DATE + " TEXT NOT NULL)";

        public static final String SQL_DROP_USERS_ ="DROP TABLE IF EXISTS " + TABLE_NAME_USERS;

        //################
        // ITEMS

        public static final String TABLE_NAME_ITEMS = "items";
        public static final String COLUMN_NAME_ITEM_NAME = "item_name";
        public static final String COLUMN_NAME_ITEM_DESCRIPTION = "item_desc";
        public static final String COLUMN_NAME_ITEM_PICTURE = "item_picture";
        public static final String COLUMN_NAME_ITEM_CODE = "item_qr_code";
        public static final String COLUMN_NAME_ITEM_CREATION_DATE = "item_creation_date";

        public static final String SQL_CREATE_ITEMS = "CREATE TABLE " + TABLE_NAME_ITEMS + " ( " + _ID +
                " INTEGER PRIMARY KEY, " +COLUMN_NAME_ITEM_NAME + " TEXT, " + COLUMN_NAME_ITEM_DESCRIPTION
                + " TEXT, " + COLUMN_NAME_ITEM_PICTURE + " TEXT, " + COLUMN_NAME_ITEM_CODE + " TEXT, " +
                COLUMN_NAME_ITEM_CREATION_DATE + " TIMESTAMP NOT NULL CURRENT_TIMESTAMP)";

        public static final String SQL_DROP_ITEMS_ ="DROP TABLE IF EXISTS " + TABLE_NAME_ITEMS;



        //################
        // BACKPACKS

        public static final String TABLE_NAME_BACKPACK = "backpacks";
        public static final String COLUMN_NAME_BACKPACK_NAME = "backpack_name";
        public static final String COLUMN_NAME_NUMBER_ITEMS = "number_of_items";
        public static final String COLUMN_NAME_BACKPACK_CREATION_DATE = "backpack_creation_date";

        public static final String SQL_CREATE_BACKPACK = "CREATE TABLE " + TABLE_NAME_BACKPACK + " ( " + _ID +
                " INTEGER PRIMARY KEY, " +COLUMN_NAME_BACKPACK_NAME + " TEXT, " + COLUMN_NAME_NUMBER_ITEMS
                + " INTEGER, " + COLUMN_NAME_BACKPACK_CREATION_DATE + " TIMESTAMP NOT NULL CURRENT_TIMESTAMP)";

        public static final String SQL_DROP_BACKPACKS_ ="DROP TABLE IF EXISTS " + TABLE_NAME_BACKPACK;

        //################
        // ITEMS + BACKPACKS

        public static final String TABLE_NAME_ITEM_BACKPACK = "item_backpack";
        public static final String COLUMN_NAME_BACKPACK_ID = "backpack_id";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";

        public static final String SQL_CREATE_ITEM_BACKPACK = "CREATE TABLE " + TABLE_NAME_ITEM_BACKPACK +
                " ( " + COLUMN_NAME_BACKPACK_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME_ITEM_ID +
                " INTEGER PRIMARY KEY)";

        public static final String SQL_DROP_ITEM_BACKPACK_ ="DROP TABLE IF EXISTS " + TABLE_NAME_ITEM_BACKPACK;

        //################
        // ROUTES

        public static final String TABLE_NAME_ROUTES = "routes";
        public static final String COLUMN_NAME_ROUTE_NAME = "route_name";
        public static final String COLUMN_NAME_ROUTE_DISTANCE = "route_distance";
        public static final String COLUMN_NAME_START_LOCATION = "start_location";
        public static final String COLUMN_NAME_END_LOCATION = "end_location";
        public static final String COLUMN_NAME_ROUTE_DIFFICULTY = "route_difficulty";
        public static final String COLUMN_NAME_ROUTE_RATING = "route_rating";
        public static final String COLUMN_NAME_ROUTE_CREATION_DATE = "route_creation_date";

        public static final String SQL_CREATE_ROUTES = "CREATE TABLE " + TABLE_NAME_ROUTES + " ( " + _ID +
                " INTEGER PRIMARY KEY, " +COLUMN_NAME_ROUTE_NAME + " TEXT, " + COLUMN_NAME_ROUTE_DISTANCE
                + " FLOAT, " + COLUMN_NAME_START_LOCATION + " TEXT, " + COLUMN_NAME_END_LOCATION + " TEXT, " +
                COLUMN_NAME_ROUTE_DIFFICULTY + " INTEGER, " +
                COLUMN_NAME_ROUTE_RATING + " INTEGER, " +
                COLUMN_NAME_ROUTE_CREATION_DATE + " TIMESTAMP NOT NULL CURRENT_TIMESTAMP)";

        public static final String SQL_DROP_ROUTES_ ="DROP TABLE IF EXISTS " + TABLE_NAME_ROUTES;

        //################
        // EVENTS

        public static final String TABLE_NAME_EVENTS = "events";
        public static final String COLUMN_NAME_EVENT_NAME = "event_name";
        public static final String COLUMN_NAME_EVENT_DESCRIPTION = "event_desc";
        public static final String COLUMN_NAME_EVENT_DATE = "event_date";
        public static final String COLUMN_NAME_EVENT_BACKPACK = "backpack_id";
        public static final String COLUMN_NAME_EVENT_ROUTE = "route_id";

        public static final String SQL_CREATE_EVENTS = "CREATE TABLE " + TABLE_NAME_EVENTS + " ( " + _ID +
                " INTEGER PRIMARY KEY, " + COLUMN_NAME_EVENT_NAME + " TEXT, " + COLUMN_NAME_EVENT_DESCRIPTION +
                " TEXT, " + COLUMN_NAME_EVENT_DATE + " TIMESTAMP, " + COLUMN_NAME_EVENT_BACKPACK + " INTEGER, " +
                COLUMN_NAME_EVENT_ROUTE + " INTEGER)";

        public static final String SQL_DROP_EVENTS_ ="DROP TABLE IF EXISTS " + TABLE_NAME_EVENTS;



    }
}
