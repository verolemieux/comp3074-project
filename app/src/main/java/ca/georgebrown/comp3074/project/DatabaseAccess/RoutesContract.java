package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

public class RoutesContract {

    public class RoutesEntity implements BaseColumns {

        public static final String TABLE_NAME_ROUTES = "routes";
        public static final String COLUMN_NAME_ROUTE_NAME = "route_name";
        public static final String COLUMN_NAME_ROUTE_DISTANCE = "route_distance";
        public static final String COLUMN_NAME_START_LOCATION = "start_location";
        public static final String COLUMN_NAME_END_LOCATION = "end_location";
        public static final String COLUMN_NAME_ROUTE_DIFFICULTY = "route_difficulty";
        public static final String COLUMN_NAME_ROUTE_RATING = "route_rating";
        public static final String COLUMN_NAME_ROUTE_CREATION_DATE = "route_creation_date";
        public static final String COLUMN_NAME_USER_EMAIL_ROUTES = "email";

        public static final String SQL_CREATE_ROUTES = "CREATE TABLE " + TABLE_NAME_ROUTES + " ( " + _ID +
                " INTEGER PRIMARY KEY, " +COLUMN_NAME_ROUTE_NAME + " TEXT, " + COLUMN_NAME_ROUTE_DISTANCE
                + " FLOAT, " + COLUMN_NAME_START_LOCATION + " TEXT, " + COLUMN_NAME_END_LOCATION + " TEXT, " +
                COLUMN_NAME_ROUTE_DIFFICULTY + " INTEGER, " +
                COLUMN_NAME_ROUTE_RATING + " INTEGER, " +
                COLUMN_NAME_ROUTE_CREATION_DATE + " TEXT, " +
                COLUMN_NAME_USER_EMAIL_ROUTES + " TEXT)";

        public static final String SQL_DROP_ROUTES_ ="DROP TABLE IF EXISTS " + TABLE_NAME_ROUTES;
    }
}
