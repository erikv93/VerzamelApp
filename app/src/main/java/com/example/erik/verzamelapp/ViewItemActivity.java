package com.example.erik.verzamelapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.erik.verzamelapp.helpers.CollectionDBContract;
import com.example.erik.verzamelapp.helpers.CollectionDatabaseHelper;

public class ViewItemActivity extends AppCompatActivity {

    CollectionDatabaseHelper cDbHelper = new CollectionDatabaseHelper(this);
    SQLiteDatabase db;

    TextView nameTv;
    TextView descriptionTv;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        db = cDbHelper.getWritableDatabase();

        Intent intent = getIntent();
        id = intent.getLongExtra("ITEM_ID", 0);

        nameTv = (TextView)findViewById(R.id.nameTextView2);
        descriptionTv = (TextView)findViewById(R.id.descriptionTextView);

        String[] projection = {
                CollectionDBContract.ItemEntry.COLUMN_NAME_NAME,
                CollectionDBContract.ItemEntry.COLUMN_NAME_DESCRIPTION,
        };

        String selection = CollectionDBContract.ItemEntry._ID + " = " + Long.toString(id);

        Cursor cursor = db.query(
                CollectionDBContract.ItemEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                CollectionDBContract.ItemEntry.COLUMN_NAME_NAME + " DESC"
        );

        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndexOrThrow(CollectionDBContract.ItemEntry.COLUMN_NAME_NAME));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(CollectionDBContract.ItemEntry.COLUMN_NAME_DESCRIPTION));

        nameTv.setText(name);
        descriptionTv.setText(description);


    }
}
