package Controller_all;


import android.app.Application;
import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import Call_back.Get_data;
import Functionality_all.DBAcessClass;
import Functionality_all.Dialog_custom;

public class Controller extends Application{
    private DBAcessClass dbAcessClass;
    private Dialog_custom dialog_custom = new Dialog_custom();


    public void dialog_custom(Object...args){
        dialog_custom.dialog_custom(args);
    }

    public void init_db(Context context){
        dbAcessClass = new DBAcessClass(context);
    }

    //Add data in DataBase
    public void Db_data_add(String name, String score, String table_name) {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("name", name);
        contentvalues.put("score", score);
        dbAcessClass.insert(table_name, contentvalues);
    }

    public boolean is_data_available(String query){
        if (dbAcessClass.isDataAvailable(query)) {
            return  true;
        } else {
            return  false;
        }
    }

    public ArrayList<HashMap<String,Object>> read_data(String query){
        return dbAcessClass.read(query);
    }

}