package com.example.erik.verzamelapp.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Erik on 3-3-2016.
 */
public class CollectionDatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "CollectionDatabase";
    private final Context myContext;


    public CollectionDatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.myContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
