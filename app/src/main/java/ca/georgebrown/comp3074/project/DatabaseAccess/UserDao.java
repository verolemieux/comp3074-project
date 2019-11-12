package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDao{
    final DBHelper dbHelper;
    public UserDao(Context context){
        dbHelper = new DBHelper(context);
    }
    public long addUser(String email, String user_name, String password){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBContract.DBEntity.COLUMN_NAME_USER_EMAIL, email);
        cv.put(DBContract.DBEntity.COLUMN_NAME_USER_NAME, user_name);
        cv.put(DBContract.DBEntity.COLUMN_NAME_PASSWORD, password);
        cv.put(DBContract.DBEntity.COLUMN_NAME_ENROLLMENT_DATE,  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return db.insert(DBContract.DBEntity.TABLE_NAME_USERS, null, cv);
    }
}
