package com.example.erik.verzamelapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
    Long id;
    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        descriptionEditText = (EditText)findViewById(R.id.descriptionEditText);
        db = cDbHelper.getWritableDatabase();

        Intent editIntent = getIntent();
        if (editIntent.hasExtra("ITEM_ID")) {
            id = editIntent.getLongExtra("ITEM_ID", 0);
            edit = true;

            String[] projection = {
                    ItemEntry.COLUMN_NAME_NAME,
                    ItemEntry.COLUMN_NAME_DESCRIPTION,
            };

            String selection = ItemEntry._ID + " = " + Long.toString(id);

            Cursor cursor = db.query(
                    ItemEntry.TABLE_NAME,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    ItemEntry.COLUMN_NAME_NAME + " DESC"
            );

            cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_DESCRIPTION));

            nameEditText.setText(name);
            descriptionEditText.setText(description);
        }

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

        Intent intent = new Intent(this, MainActivity.class);

        if (!edit) {
            db.insert(ItemEntry.TABLE_NAME, null, values);
        } else {
            db.update(ItemEntry.TABLE_NAME, values, "_ID = ?", new String[]{Long.toString(id)});
        }
        intent.putExtra("ITEM_NAME", nameEditText.getText().toString());
        startActivity(intent);
    }

}
