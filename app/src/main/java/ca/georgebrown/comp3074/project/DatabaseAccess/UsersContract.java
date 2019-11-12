package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

public class UsersContract {

    private UsersContract() {}

    public class UsersEntity implements BaseColumns {

        public static final String TABLE_NAME_USERS = "users";
        public static final String COLUMN_NAME_USER_EMAIL = "email";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_ENROLLMENT_DATE = "enrollment_date";

        public static final String SQL_CREATE_USERS = "CREATE TABLE " + TABLE_NAME_USERS + " ( " + COLUMN_NAME_USER_EMAIL +
                " TEXT PRIMARY KEY, " + COLUMN_NAME_USER_NAME + " TEXT, " + COLUMN_NAME_PASSWORD
                + " TEXT, " + COLUMN_NAME_ENROLLMENT_DATE + " TEXT)";

        public static final String SQL_DROP_USERS_ = "DROP TABLE IF EXISTS " + TABLE_NAME_USERS;
    }
}
