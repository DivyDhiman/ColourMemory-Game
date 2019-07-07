package Functionality_all;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Divy dhiman
 */

public class DBAcessClass {

    String dbName = "Colour Memory";
    Context mContext;
    DBWork dbWork;
    SQLiteDatabase sqLiteDatabase;
    public String data_all;
    private ArrayList<HashMap<String, Object>> list;
    private HashMap<String, Object> list_sub;

    //DBAcessClass constructor
    public DBAcessClass(Context con) {
        mContext = con;
    }

    //Call build function of Data Base
    public void buildDB() {
        dbWork = new DBWork(mContext, dbName, 1);
        sqLiteDatabase = dbWork.getWritableDatabase();
    }

    //Insert all data on Create Goal
    public boolean insert(String tableName, ContentValues contentValues) {
        long resultInserting = sqLiteDatabase.insert(tableName, "nothing", contentValues);
        if (resultInserting > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Read all data on View goal
    public ArrayList<HashMap<String, Object>> read(String query) {
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list_sub = new HashMap<>();
                            list_sub.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                            list_sub.put("name", cursor.getString(cursor.getColumnIndex("name")));
                            list_sub.put("score", cursor.getString(cursor.getColumnIndex("score")));
                            list.add(list_sub);
                cursor.moveToNext();
            }
        }
        return list;
    }


    //Delete any goal
    public void delete(String query) {
        sqLiteDatabase.execSQL(query);
    }

    //Update previous information
    public void update(String query) {
        sqLiteDatabase.execSQL(query);
    }

    //Check is Data Available in Data Base or not
    public boolean isDataAvailable(String query) {
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}