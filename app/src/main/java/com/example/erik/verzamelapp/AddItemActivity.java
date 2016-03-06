package com.example.erik.verzamelapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.example.erik.verzamelapp.helpers.CollectionDBContract.ItemEntry;

import com.example.erik.verzamelapp.helpers.CollectionDatabaseHelper;

public class AddItemActivity extends AppCompatActivity {

    CollectionDatabaseHelper cDbHelper = new CollectionDatabaseHelper(this);

    EditText nameEditText;
    EditText descriptionEditText;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        descriptionEditText = (EditText)findViewById(R.id.descriptionEditText);
        db = cDbHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_additem, menu);
        return true;
    }

    public void saveButtonClicked(MenuItem menuItem){
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_NAME_NAME, nameEditText.getText().toString());
        values.put(ItemEntry.COLUMN_NAME_DESCRIPTION, descriptionEditText.getText().toString());

        db.insert(ItemEntry.TABLE_NAME, null, values);
    }

}
