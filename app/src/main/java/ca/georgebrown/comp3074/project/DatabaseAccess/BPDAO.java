package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ca.georgebrown.comp3074.project.Backpack.Backpack;

public class BPDAO {
    private DBHelper dbHelper;
    private ArrayList<Backpack> backpacks = new ArrayList<Backpack>();
    public BPDAO(Context context){
        dbHelper = new DBHelper(context);
        //dbHelper.getWritableDatabase().execSQL(BPContract.BPEntity.SQL_DROP_BP);
        //dbHelper.getWritableDatabase().execSQL(BPContract.BPEntity.SQL_CREATE_BP);
    }
    public long addBackpack(String name, int numOfItems, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BPContract.BPEntity.COLUMN_NAME_BP_NAME, name);
        cv.put(BPContract.BPEntity.COLUMN_NAME_NUM_ITEMS, numOfItems);
        cv.put(BPContract.BPEntity.COLUMN_NAME_CREATED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        cv.put(BPContract.BPEntity.COLUMN_USER_EMAIL, email);
        return db.insert(BPContract.BPEntity.TABLE_NAME_BP, null, cv);
    }
    public void updateBP(Backpack bp, String email){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BPContract.BPEntity.COLUMN_NAME_BP_NAME, bp.getBackpack_Name());
        cv.put(BPContract.BPEntity.COLUMN_NAME_NUM_ITEMS, bp.getItem_List().size());
        cv.put(BPContract.BPEntity.COLUMN_NAME_CREATED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        cv.put(BPContract.BPEntity.COLUMN_USER_EMAIL, email);
        db.update(BPContract.BPEntity.TABLE_NAME_BP,cv,"_id="+bp.getBackpack_Id()+" and " + BPContract.BPEntity.COLUMN_USER_EMAIL+ " = '"+email+"'", null);
    }
    public void deleteBP(Backpack bp,String email ){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(BPContract.BPEntity.TABLE_NAME_BP, "_id="+bp.getBackpack_Id()+" AND "+
                BPContract.BPEntity.COLUMN_USER_EMAIL+" = '"+email+"'",null);
    }
    public Backpack getBackpackByName(String name, String email, String key){
        ArrayList<Backpack> backpacks = getAllBP(email, key);
        for(Backpack bp: backpacks){
            if(bp.getBackpack_Name().equals(name)){
                return bp;
            }
        }
        return null;
    }
    public Backpack getBackpackById(long id, String email){
        ArrayList<Backpack> backpacks = getAllBP(email, "");
        for(Backpack bp: backpacks){
            if(bp.getBackpack_Id() == id){
                return bp;
            }
        }
        return null;
    }
    public long getBPID(String name, String email, String key){
        Cursor c = getAllBPHelper(email, key);
        while(c.moveToNext()){
            String db_bp_name = c.getString(c.getColumnIndexOrThrow(BPContract.BPEntity.COLUMN_NAME_BP_NAME));
            if(db_bp_name.equals(name)){
                return c.getInt(c.getColumnIndexOrThrow(BPContract.BPEntity._ID));
            }
        }
        return -1;
    }
    public ArrayList<Backpack> getAllBP(String email, String key){
        Cursor c = getAllBPHelper(email, key);
        while(c.moveToNext()){
            Backpack bp = new Backpack(
                    c.getInt(c.getColumnIndexOrThrow(BPContract.BPEntity._ID)),
                    c.getString(c.getColumnIndexOrThrow(BPContract.BPEntity.COLUMN_NAME_BP_NAME)),
                    c.getString(c.getColumnIndexOrThrow(BPContract.BPEntity.COLUMN_USER_EMAIL))
            );
            backpacks.add(bp);
        }
        c.close();
        return backpacks;

    }
    private Cursor getAllBPHelper(String email, String key){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BPContract.BPEntity._ID,
                BPContract.BPEntity.COLUMN_NAME_BP_NAME,
                BPContract.BPEntity.COLUMN_USER_EMAIL
                /*ItemsContract.ItemsEntity._ID,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_NAME,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_DESCRIPTION,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_PICTURE,
                ItemsContract.ItemsEntity.COLUMN_NAME_ITEM_CODE*/
        };
        if(key!="") {
            String selection = BPContract.BPEntity.COLUMN_USER_EMAIL + "=? AND "+
                    BPContract.BPEntity.COLUMN_NAME_BP_NAME + " like '%"+key+"%'";
            String[] selectionArgs = {email};

            return db.query(
                    BPContract.BPEntity.TABLE_NAME_BP,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
        }
        String selection = BPContract.BPEntity.COLUMN_USER_EMAIL+"=?";
        String[] selectionArgs = {email};
        return db.query(
                BPContract.BPEntity.TABLE_NAME_BP,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
}
