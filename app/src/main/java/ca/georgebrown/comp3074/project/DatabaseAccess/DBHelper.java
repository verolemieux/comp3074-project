package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "project.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemsContract.ItemsEntity.SQL_CREATE_ITEMS);
        db.execSQL(UsersContract.UsersEntity.SQL_CREATE_USERS);
        db.execSQL(BPContract.BPEntity.SQL_CREATE_BP);
        db.execSQL(EventsContract.EventsEntity.SQL_CREATE_EVENTS);
        db.execSQL(RoutesContract.RoutesEntity.SQL_CREATE_ROUTES);
        //db.execSQL(ItemBPContract.ItempBPEntity.SQL_CREATE_ITEM_BACKPACK);
        //db.execSQL(DBContract.DBEntity.SQL_CREATE_BACKPACK);
        db.execSQL(ItemBPContract.ItempBPEntity.SQL_CREATE_ITEM_BACKPACK);
        //db.execSQL(DBContract.DBEntity.SQL_CREATE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(ItemsContract.ItemsEntity.SQL_DROP_ITEMS_);
        db.execSQL(UsersContract.UsersEntity.SQL_DROP_USERS_);
        db.execSQL(BPContract.BPEntity.SQL_DROP_BP);
        db.execSQL(EventsContract.EventsEntity.SQL_DROP_EVENTS_);
        //db.execSQL(DBContract.DBEntity.SQL_DROP_BACKPACKS_);
        //db.execSQL(DBContract.DBEntity.SQL_DROP_ITEM_BACKPACK_);
        db.execSQL(RoutesContract.RoutesEntity.SQL_DROP_ROUTES_);
        db.execSQL(ItemBPContract.ItempBPEntity.SQL_DROP_ITEM_BACKPACK_);
        //db.execSQL(DBContract.DBEntity.SQL_DROP_EVENTS_);
        onCreate(db);
    }
}
