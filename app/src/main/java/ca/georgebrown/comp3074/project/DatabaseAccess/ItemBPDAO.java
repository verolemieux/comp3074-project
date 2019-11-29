package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.Item.Item;

public class ItemBPDAO {
    private DBHelper dbHelper;
    private ItemsDAO itemsDAO;
    public ItemBPDAO(Context context){
        itemsDAO = new ItemsDAO(context);
        dbHelper = new DBHelper(context);
        //dbHelper.getWritableDatabase().execSQL(ItemBPContract.ItempBPEntity.SQL_DROP_ITEM_BACKPACK_);
        //dbHelper.getWritableDatabase().execSQL(ItemBPContract.ItempBPEntity.SQL_CREATE_ITEM_BACKPACK);
    }
    public void addItemToBP(long bp, long i, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ItemBPContract.ItempBPEntity.COLUMN_NAME_BACKPACK_ID, bp);
        cv.put(ItemBPContract.ItempBPEntity.COLUMN_NAME_ITEM_ID, i);
        cv.put(ItemBPContract.ItempBPEntity.COLUMN_NAME_USER_EMAIL, email);
        db.insert(ItemBPContract.ItempBPEntity.TABLE_NAME_ITEM_BACKPACK, null, cv);
    }
    public void deleteItemToBP(long bp, long i, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = ItemBPContract.ItempBPEntity.COLUMN_NAME_USER_EMAIL + " = '"+email+"'"+" AND "+
                ItemBPContract.ItempBPEntity.COLUMN_NAME_ITEM_ID+" = "+i+" AND "+ ItemBPContract.ItempBPEntity.COLUMN_NAME_BACKPACK_ID
                + " = "+ bp;
        db.delete(ItemBPContract.ItempBPEntity.TABLE_NAME_ITEM_BACKPACK,where,null);
    }
    public void deleteBP(long bp, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = ItemBPContract.ItempBPEntity.COLUMN_NAME_USER_EMAIL + " = '"+email+"'"+" AND "+
                ItemBPContract.ItempBPEntity.COLUMN_NAME_BACKPACK_ID+" = "+bp;
        db.delete(ItemBPContract.ItempBPEntity.TABLE_NAME_ITEM_BACKPACK, where, null);
    }
    public ArrayList<Item> getItemsFromBP(long bp, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Item> items = new ArrayList<>();
        Cursor c = getItemsFromBPHelper(bp,email);
        while (c.moveToNext()) {
            Item i = itemsDAO.getItemDetails(c.getInt(c.getColumnIndexOrThrow(ItemBPContract.ItempBPEntity.COLUMN_NAME_ITEM_ID)), email);
            if(i!=null){
                items.add(i);
            }
        }
        return items;
    }
    private Cursor getItemsFromBPHelper(long bp, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                ItemBPContract.ItempBPEntity.COLUMN_NAME_ITEM_ID
        };
        String selection = ItemBPContract.ItempBPEntity.COLUMN_NAME_BACKPACK_ID+" = "+bp+" AND "+
                ItemBPContract.ItempBPEntity.COLUMN_NAME_USER_EMAIL+ " = '"+email+"'";
        return db.query(
                ItemBPContract.ItempBPEntity.TABLE_NAME_ITEM_BACKPACK,
                projection,
                selection,
                null,
                null,
                null,
                null
        );
    }
}
