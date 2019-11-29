package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ca.georgebrown.comp3074.project.User.User;

public class UsersDAO {
    private DBHelper dbHelper;
    public UsersDAO(Context context){
        dbHelper = new DBHelper(context);
    }
    public long addUser(String email, String user_name, String password){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UsersContract.UsersEntity.COLUMN_NAME_USER_EMAIL, email);
        cv.put(UsersContract.UsersEntity.COLUMN_NAME_USER_NAME, user_name);
        cv.put(UsersContract.UsersEntity.COLUMN_NAME_PASSWORD, password);
        cv.put(UsersContract.UsersEntity.COLUMN_NAME_ENROLLMENT_DATE,  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return db.insert(UsersContract.UsersEntity.TABLE_NAME_USERS, null, cv);
    }
    public boolean userExist(String email)
    {
        ArrayList<String> userList = new ArrayList<String>();


        Cursor c = getUsers(email);
        if(c.moveToNext())
        {
            return true;
        }
        return false;

    }
    public Cursor getUsers(String email)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {UsersContract.UsersEntity.COLUMN_NAME_USER_EMAIL};
        String selection = UsersContract.UsersEntity.COLUMN_NAME_USER_EMAIL + "=?";
        String[] selectionArgs = {email};

        return db.query(
                UsersContract.UsersEntity.TABLE_NAME_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
    public User validateUser(String email, String password){
        //CREATE USER INSTANCE AND SET VALUES TO DATABASE VALUES AND THEN RETURN THE OBJECT
        String[] columns = {UsersContract.UsersEntity.COLUMN_NAME_USER_EMAIL,
                UsersContract.UsersEntity.COLUMN_NAME_USER_NAME,
                UsersContract.UsersEntity.COLUMN_NAME_PASSWORD,
                UsersContract.UsersEntity.COLUMN_NAME_ENROLLMENT_DATE,
        };
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = UsersContract.UsersEntity.COLUMN_NAME_USER_EMAIL+" =? "+
                " AND " + UsersContract.UsersEntity.COLUMN_NAME_PASSWORD + " =?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(UsersContract.UsersEntity.TABLE_NAME_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        if(cursor!=null && cursor.moveToNext()){
            User user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersEntity.COLUMN_NAME_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersEntity.COLUMN_NAME_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersEntity.COLUMN_NAME_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersEntity.COLUMN_NAME_ENROLLMENT_DATE))
            );
            cursor.close();
            db.close();
            return user;
        }
        cursor.close();
        db.close();
        return null;
    }



}