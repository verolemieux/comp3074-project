package ca.georgebrown.comp3074.project.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Route.Route;
import ca.georgebrown.comp3074.project.User.User;

public class RoutesDAO {

    private DBHelper dbHelper;
    Cursor c;

    private ArrayList<Route> routeList = new ArrayList<Route>();

    public RoutesDAO(Context context)
    {
        dbHelper = new DBHelper(context);
        dbHelper.getReadableDatabase();
    }
    public Route getRouteByName(String name, String email, String key){
        ArrayList<Route> routes = getRouteList(email, key);
        for(Route route: routes){
            if(route.getRoute_Name().equals(name)){
                return route;
            }
        }
        return null;
    }

    public ArrayList<Route> getRouteList(String email, String key)
    {
        c = getAllRoutes(email, key);
        routeList.clear();

        while(c.moveToNext())
        {
            Route r = new Route(
                    c.getInt(c.getColumnIndexOrThrow(RoutesContract.RoutesEntity._ID)),
                    c.getString(c.getColumnIndexOrThrow(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_NAME)),
                    c.getFloat(c.getColumnIndexOrThrow(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DISTANCE)),
                    c.getInt(c.getColumnIndexOrThrow(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DIFFICULTY)),
                    c.getInt(c.getColumnIndexOrThrow(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_RATING)),
                    c.getString(c.getColumnIndexOrThrow(RoutesContract.RoutesEntity.COLUMN_NAME_START_LOCATION)),
                    c.getString(c.getColumnIndexOrThrow(RoutesContract.RoutesEntity.COLUMN_NAME_END_LOCATION)));

            routeList.add(r);
        }
        c.close();
        return routeList;
    }

    public Cursor getAllRoutes(String email, String key)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                RoutesContract.RoutesEntity._ID,
                RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_NAME,
                RoutesContract.RoutesEntity.COLUMN_NAME_START_LOCATION,
                RoutesContract.RoutesEntity.COLUMN_NAME_END_LOCATION,
                RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DISTANCE,
                RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DIFFICULTY,
                RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_RATING,
                RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_CREATION_DATE
        };

        if(key != "") {
            String selection = RoutesContract.RoutesEntity.COLUMN_NAME_USER_EMAIL_ROUTES + "=? AND " +
                    RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_NAME + " LIKE '%" + key + "%'";
            String[] selectionArgs = {email};

            return db.query(
                    RoutesContract.RoutesEntity.TABLE_NAME_ROUTES,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
        }
        String selection = RoutesContract.RoutesEntity.COLUMN_NAME_USER_EMAIL_ROUTES + "=?";
        String[] selectionArgs = {email};

        return db.query(
                RoutesContract.RoutesEntity.TABLE_NAME_ROUTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public void addRoute(Route r, User u)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_NAME, r.getRoute_Name());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DISTANCE, r.getRoute_Distance());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DIFFICULTY, r.getRoute_Difficulty());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_RATING, r.getRoute_Rating());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_START_LOCATION, r.getRoute_Start_Location());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_END_LOCATION, r.getRoute_End_Location());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_USER_EMAIL_ROUTES, u.getEmail());

        db.insert(RoutesContract.RoutesEntity.TABLE_NAME_ROUTES, null, cv);

    }

    public void editRoute(Route r, User u, int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_NAME, r.getRoute_Name());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DISTANCE, r.getRoute_Distance());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_DIFFICULTY, r.getRoute_Difficulty());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_ROUTE_RATING, r.getRoute_Rating());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_START_LOCATION, r.getRoute_Start_Location());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_END_LOCATION, r.getRoute_End_Location());
        cv.put(RoutesContract.RoutesEntity.COLUMN_NAME_USER_EMAIL_ROUTES, u.getEmail());

        db.update(RoutesContract.RoutesEntity.TABLE_NAME_ROUTES, cv, "_ID=" + id + " and email='" +
                u.getEmail() + "'", null);
    }

    public void deleteRoute(User u, int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(RoutesContract.RoutesEntity.TABLE_NAME_ROUTES, "_ID=" + id + " and email='" +
                u.getEmail() + "'", null);
    }

}
