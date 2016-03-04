package com.example.erik.verzamelapp.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.erik.verzamelapp.helpers.CollectionDBContract.ItemEntry;

/**
 * Created by Erik on 3-3-2016.
 */
public class CollectionDatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Collections.db";


    public CollectionDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COLLECTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_COLLECTION);
        onCreate(db);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_COLLECTION =
            " CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
            ItemEntry._ID + " INTEGER PRIMARY KEY," +
            ItemEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            ItemEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
            " );";

    private static final String SQL_DELETE_COLLECTION = "DROP TABLE IF EXISTS" + ItemEntry.TABLE_NAME;
}
