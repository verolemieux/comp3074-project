package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

public class ItemsContract  {

    private ItemsContract() {}

    public class ItemsEntity implements BaseColumns {

        public static final String TABLE_NAME_ITEMS = "items";
        public static final String COLUMN_NAME_ITEM_NAME = "item_name";
        public static final String COLUMN_NAME_ITEM_DESCRIPTION = "item_desc";
        public static final String COLUMN_NAME_ITEM_PICTURE = "item_picture";
        public static final String COLUMN_NAME_ITEM_CODE = "item_qr_code";
        public static final String COLUMN_NAME_ITEM_CREATION_DATE = "item_creation_date";
        public static final String COLUMN_NAME_USER_EMAIL_ITEMS = "email";

        public static final String SQL_CREATE_ITEMS = "CREATE TABLE " + TABLE_NAME_ITEMS + " ( " + _ID +
                " INTEGER PRIMARY KEY, " + COLUMN_NAME_ITEM_NAME + " TEXT, " + COLUMN_NAME_ITEM_DESCRIPTION
                + " TEXT, " + COLUMN_NAME_ITEM_PICTURE + " TEXT, " + COLUMN_NAME_ITEM_CODE + " TEXT, " +
                COLUMN_NAME_ITEM_CREATION_DATE + " TEXT, " + COLUMN_NAME_USER_EMAIL_ITEMS + " TEXT)";

        public static final String SQL_DROP_ITEMS_ = "DROP TABLE IF EXISTS " + TABLE_NAME_ITEMS;
    }
}
