package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.provider.BaseColumns;

public class ItemBPContract {
    private ItemBPContract(){}
    public class ItempBPEntity implements BaseColumns{
        public static final String TABLE_NAME_ITEM_BACKPACK = "item_backpack";
        public static final String COLUMN_NAME_BACKPACK_ID = "backpack_id";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_USER_EMAIL = "email";

        public static final String SQL_CREATE_ITEM_BACKPACK = "CREATE TABLE " + TABLE_NAME_ITEM_BACKPACK +
                " ( " + COLUMN_NAME_BACKPACK_ID + " INTEGER, " + COLUMN_NAME_ITEM_ID+
                " INTEGER, "+ COLUMN_NAME_USER_EMAIL+" TEXT)";

        public static final String SQL_DROP_ITEM_BACKPACK_ ="DROP TABLE IF EXISTS " + TABLE_NAME_ITEM_BACKPACK;
    }

}
