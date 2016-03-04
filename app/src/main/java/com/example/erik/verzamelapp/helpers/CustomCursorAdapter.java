package com.example.erik.verzamelapp.helpers;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.erik.verzamelapp.helpers.CollectionDBContract.ItemEntry;

import com.example.erik.verzamelapp.R;

/**
 * Created by Erik on 4-3-2016.
 */
public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflator;

    public CustomCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.item_list_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.itemRowName);
        TextView description = (TextView) view.findViewById(R.id.itemRowDescription);

        name.setText(cursor.getString(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_NAME)));
        description.setText(cursor.getString(cursor.getColumnIndexOrThrow(ItemEntry.COLUMN_NAME_DESCRIPTION)));
    }
}
