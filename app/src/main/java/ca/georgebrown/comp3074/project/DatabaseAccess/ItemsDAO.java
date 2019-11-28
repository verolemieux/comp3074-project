package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.sql.*;
import java.io.*;
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
        //dbHelper.getWritableDatabase().execSQL(ItemsContract.ItemsEntity.SQL_DROP_ITEMS_);
        //dbHelper.getWritableDatabase().execSQL(ItemsContract.ItemsEntity.SQL_CREATE_ITEMS);
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
            byte[] itemPhoto = c.getBlob(c.getColumnIndex(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_PICTURE));
            byte[] qrCode = c.getBlob(c.getColumnIndex(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_CODE));
            if(qrCode != null)
            {
                Log.d("a", "success");
                //Bitmap qrBM = BitmapFactory.decodeByteArray(qrCode, 0, qrCode.length);
                i.setItem_QR_Code(qrCode);
            }

            if(itemPhoto != null)
            {
                i.setItem_Picture(itemPhoto);
            }

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
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_CODE,
                ItemsContract.ItemsEntity.COLUMN_NAME_BP_ID
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
        byte[] picArray = null;
        byte[] qrArray = null;

        /*if(i.getItem_Picture()!= null)
        {
            ByteArrayOutputStream bosPic = new ByteArrayOutputStream();
            i.getItem_Picture().compress(Bitmap.CompressFormat.PNG, 100, bosPic);
            picArray = bosPic.toByteArray();
        }*/

        /*if(i.getItem_QR_Code() != null)
        {
            ByteArrayOutputStream bosQR = new ByteArrayOutputStream();
            i.getItem_QR_Code().compress(Bitmap.CompressFormat.PNG, 100, bosQR);
            qrArray = bosQR.toByteArray();
            Log.d("A", "Success to byte array");
        }*/



        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_NAME, i.getItem_Name());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_DESCRIPTION, i.getDescription());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_PICTURE, i.getItem_Picture());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_CODE, i.getItem_QR_Code());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_USER_EMAIL_ITEMS, u.getEmail());

        db.insert(ItemsContract.ItemsEntity.TABLE_NAME_ITEMS, null, cv);
    }
    public void addToBP(Item i, long bp_id, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_BP_ID, bp_id);
        db.update(ItemsContract.ItemsEntity.TABLE_NAME_ITEMS, cv,
                "_id="+i.getItem_Id()+" AND "+ ItemsContract.ItemsEntity.COLUMN_NAME_USER_EMAIL_ITEMS+" ='"+email+"'",
                null);
    }
    public void removeAllItemsFromBP(String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_BP_ID, 0);
        db.update(ItemsContract.ItemsEntity.TABLE_NAME_ITEMS, cv,
                ItemsContract.ItemsEntity.COLUMN_NAME_BP_ID+" != 0 " +
                        "AND "+ ItemsContract.ItemsEntity.COLUMN_NAME_USER_EMAIL_ITEMS+" ='"+email+"'", null);
    }
    public ArrayList<Item> getBPItems(long bp_id, String email){
        Cursor c = getAllItems(email);
        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()){
            long backpack_id = c.getInt(c.getColumnIndexOrThrow(ItemsContract.ItemsEntity.COLUMN_NAME_BP_ID));
            if(backpack_id == bp_id){
                Item i = new Item(c.getInt(c.getColumnIndexOrThrow(DBContract.DBEntity._ID))
                        , c.getString(c.getColumnIndexOrThrow(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_NAME))
                        , c.getString(c.getColumnIndexOrThrow(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_DESCRIPTION)));
                items.add(i);
            }
        }
        return items;
    }

    public void editItem(Item i, User u, int id)
    {
        ByteArrayOutputStream bosPic = new ByteArrayOutputStream();
        //i.getItem_Picture().compress(Bitmap.CompressFormat.PNG, 100, bosPic);
        byte[] picArray = bosPic.toByteArray();

        ByteArrayOutputStream bosQR = new ByteArrayOutputStream();
        //i.getItem_QR_Code().compress(Bitmap.CompressFormat.PNG, 100, bosQR);
        byte[] qrArray = bosPic.toByteArray();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_NAME, i.getItem_Name());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_DESCRIPTION, i.getDescription());
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_PICTURE, picArray);
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_CODE, qrArray);
        cv.put(ItemsContract.ItemsEntity.COLUMN_NAME_USER_EMAIL_ITEMS, u.getEmail());

        db.update(ItemsContract.ItemsEntity.TABLE_NAME_ITEMS, cv, "_ID="+id + " and email='"
        + u.getEmail() + "'", null);
    }

    public void deleteItem(int id, User u)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(ItemsContract.ItemsEntity.TABLE_NAME_ITEMS, "_ID="+id + " and email='"
        + u.getEmail() + "'", null);
    }
}
