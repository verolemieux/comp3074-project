package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.User.User;

public class ItemsDAO {

    private DBHelper dbHelper;
    Cursor c;
    private ArrayList<Item> ItemList = new ArrayList<Item>();

    public ItemsDAO(Context context)
    {
        dbHelper = new DBHelper(context);
        dbHelper.getReadableDatabase();
    }

    public ArrayList<Item> getItems(String email)
    {

        c = getAllItems(email);
        ItemList.clear();
        while(c.moveToNext())
        {
            Item i = new Item(c.getInt(c.getColumnIndexOrThrow(DBContract.DBEntity._ID))
            , c.getString(c.getColumnIndexOrThrow(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_NAME))
             , c.getString(c.getColumnIndexOrThrow(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_DESCRIPTION)));
            // , c.getString(c.getColumnIndexOrThrow(DBContract.DBEntity.COLUMN_NAME_ITEM_PICTURE))
           // , c.getString(c.getColumnIndexOrThrow(DBContract.DBEntity.COLUMN_NAME_ITEM_CODE)));
            ItemList.add(i);
        }
        c.close();
        return ItemList;
    }

    private Cursor getAllItems(String email)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ItemsContract.ItemsEntity._ID,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_NAME,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_DESCRIPTION,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_PICTURE,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_CODE
        };
        String selection = ItemsContract.ItemsEntity.COLUMN_NAME_USER_EMAIL_ITEMS + "=?";
        String[] selectionArgs = {email};

        return db.query(
                ItemsContract.ItemsEntity.TABLE_NAME_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public void addItem(Item i, User u)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_NAME, i.getItem_Name());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_DESCRIPTION, i.getDescription());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_PICTURE, i.getItem_Picture());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_CODE, i.getItem_QR_Code());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_USER_EMAIL_ITEMS, u.getEmail());

        db.insert(ItemsContract.ItemsEntity.TABLE_NAME_ITEMS, null, cv);
    }
}
