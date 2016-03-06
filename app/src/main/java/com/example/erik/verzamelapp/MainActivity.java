package com.example.erik.verzamelapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import com.example.erik.verzamelapp.helpers.CollectionDBContract.ItemEntry;

import com.example.erik.verzamelapp.helpers.CollectionDatabaseHelper;
import com.example.erik.verzamelapp.helpers.CustomCursorAdapter;

public class MainActivity extends AppCompatActivity{

    CollectionDatabaseHelper cDbHelper = new CollectionDatabaseHelper(this);
    SQLiteDatabase db;
    CustomCursorAdapter customCursorAdapter;

    String[] projection = {
            ItemEntry._ID,
            ItemEntry.COLUMN_NAME_NAME,
            ItemEntry.COLUMN_NAME_DESCRIPTION,
    };
    String sortOrder = ItemEntry.COLUMN_NAME_NAME + " DESC";
    Cursor c;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        Intent addItemIntent = getIntent();;
        if (addItemIntent.getStringExtra("ITEM_NAME") != null) {
            String snackbarString = "Added \'" + addItemIntent.getStringExtra("ITEM_NAME") + "\'";
            showSnackbar(snackbarString);
        }

        db = cDbHelper.getWritableDatabase();

        c  = db.query(
                ItemEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to delete this item?");
        builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which) {
            }
        });


        customCursorAdapter = new CustomCursorAdapter(this, c, 0);

        final ListView listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(customCursorAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Long _id = id;
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selection = ItemEntry._ID + " LIKE ?";
                        db.delete(ItemEntry.TABLE_NAME, selection, new String[]{Long.toString(_id)});
                        refreshListView();
                    }
                });
                builder.setNeutralButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                        intent.putExtra("ITEM_ID", _id);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }

    public void showSnackbar(String string){
        Snackbar.make(findViewById(R.id.coordinaterlayout), string, Snackbar.LENGTH_LONG).show();
    }

    public void refreshListView(){
       Cursor newCursor  = db.query(
                ItemEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        customCursorAdapter.swapCursor(newCursor);
        customCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
