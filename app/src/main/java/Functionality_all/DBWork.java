package Functionality_all;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Divy dhiman
 */

//Centralise SQL Data Base Call
public class DBWork extends SQLiteOpenHelper{

    String Colour_Memory = "Colour_Memory";

    public DBWork(Context context, String name, int version) {
        super(context, name,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Data Base
        db.execSQL("create table "+Colour_Memory+" (_id Integer Primary key autoincrement, name Text, score Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}