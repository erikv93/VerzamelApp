package com.example.erik.verzamelapp.helpers;

import android.provider.BaseColumns;

/**
 * Created by Erik on 4-3-2016.
 */
public final class CollectionDBContract {
    public CollectionDBContract() {}

    public static abstract class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "collection";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}
