package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

public class BPContract {
    private BPContract(){}
    public class BPEntity implements BaseColumns{
        public static final String TABLE_NAME_BP = "backpacks";
        public static final String COLUMN_NAME_BP_NAME = "bp_name";
        public static final String COLUMN_NAME_NUM_ITEMS = "num_of_items";
        public static final String COLUMN_NAME_CREATED = "bp_created";
        public static final String COLUMN_USER_EMAIL = "user_email";

        public static final String SQL_CREATE_BP = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_BP + " ( "+_ID+
                " INTEGER PRIMARY KEY, " + COLUMN_NAME_BP_NAME + " TEXT, " + COLUMN_NAME_NUM_ITEMS + " INTEGER, "+
                COLUMN_NAME_CREATED + " TEXT, "+ COLUMN_USER_EMAIL+" TEXT)" ;

        public static final String SQL_DROP_BP = "DROP TABLE IF EXISTS "+ TABLE_NAME_BP;


    }
}
