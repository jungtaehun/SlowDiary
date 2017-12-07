package com.koreatech.bcsdlab.slowdiary;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDb {
    private static final String DATABASE_NAME = "TEST_DB";
    private static final String TABLE_NAME = "TEST_TABLE";
    private static final int DATABASE_VERSION = 1;
    private final DatabaseOpenHelper mDatabaseOpenHelper;

    //The columns
    public static final String ID = BaseColumns._ID;
    public static final String TITLE = "Title";
    public static final String CONTENT = "Content";
    public static final String WDATE = "WriteDate";
    public static final String ODATE = "OpenDate";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + WDATE + " DATETIME," + ODATE + " DATETIME," + TITLE + "," + CONTENT + ")";

    // query projection
    public static final String[] PROJECTION = {
            ID,
            WDATE,
            ODATE,
            TITLE,
            CONTENT
    };

    public TestDb(Context context) { mDatabaseOpenHelper = new DatabaseOpenHelper(context);}


    public Cursor getODate(String t) {
        return query(
                TITLE + " like '%" + t + "%'",
                null,
                PROJECTION
        );
    }

    public Cursor getTitle() { return query(null, null, PROJECTION); }
    public Cursor getTitle(String t) {
        return query(
                TITLE + " like '%" + t + "%'",
                null,
                PROJECTION
        );
    }

    //public Cursor getOpenDate() { return query(null, null, PROJECTION); }
    public Cursor getPrevious() {

        //SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
        String[] selectArg = new String[]{};
        //Cursor c = db.query(TABLE_NAME, PROJECTION , ODATE + "< DATETIME('now')", selectArg, null, null, null);
        Cursor c1 = query(ODATE + "< DATETIME('now')", selectArg, PROJECTION, null);
        return c1;
    }

    public Cursor getFuture() {
        //SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
        String[] selectArg = new String[]{};
        //String sql = "SELECT * from TEST_TABLE where OpenDate = (select max(OpenDate) from TEST_TABLE WHERE OpenDate < DATE('now') )";
        //Cursor c2 = db.query(TABLE_NAME, PROJECTION , ODATE + ">= DATETIME('now')", selectArg, null, null, null);
        Cursor c2 = query(ODATE + ">= DATETIME('now')", selectArg, PROJECTION, null);
        //Cursor c2 = db.rawQuery(sql,selectArg,null);
        return c2;
    }



    public Cursor query(String selection, String[] selectionArgs, String[] columns, String order) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_NAME);

        Cursor cursor = builder.query(
                mDatabaseOpenHelper.getReadableDatabase(),
                columns,
                selection,
                selectionArgs,
                null,
                null,
                order
        );

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor query(String selection, String[] selectionArgs, String[] columns) {
        return query(selection, selectionArgs, columns, null);
    }
}