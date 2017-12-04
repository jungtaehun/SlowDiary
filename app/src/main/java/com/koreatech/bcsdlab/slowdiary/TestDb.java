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
            "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + WDATE + "," + ODATE + "," + TITLE + "," + CONTENT + ")";

    // query projection
    public static final String[] PROJECTION = {
            ID,
            WDATE,
            ODATE,
            TITLE,
            CONTENT
    };

    public TestDb(Context context) { mDatabaseOpenHelper = new DatabaseOpenHelper(context);}

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
            loadTestDb(db);
        }

        /**
         * Called when the database needs to be upgraded. The implementation
         * should use this method to drop tables, add tables, or do anything else it
         * needs to upgrade to the new schema version.
         * <p/>
         * <p>
         * The SQLite ALTER TABLE documentation can be found
         * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
         * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
         * you can use ALTER TABLE to rename the old table, then create the new table and then
         * populate the new table with the contents of the old table.
         * </p><p>
         * This method executes within a transaction.  If an exception is thrown, all changes
         * will automatically be rolled back.
         * </p>
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(getClass().getName(), "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        private void loadTestDb(SQLiteDatabase db) {
            final Resources resources = mHelperContext.getResources();
            //InputStream inputStream = resources.openRawResource(R.raw.test_db);

            for(int i = 0; i < 10; i++) {
                ContentValues initialValues = new ContentValues();
                initialValues.put(TITLE, "Test Title");
                initialValues.put(CONTENT, "Test Content");
                initialValues.put(WDATE, String.valueOf(new Date()));
                initialValues.put(ODATE, String.valueOf(new Date(2017,12,10)));
                long id = db.insert(TABLE_NAME, null, initialValues);
            }
            /*
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    ContentValues initialValues = new ContentValues();
                    initialValues.put(TITLE, line);

                    long id = db.insert(TABLE_NAME, null, initialValues);
                    if (id < 0) {
                        Log.e(getClass().getName(), "unable to add test db: " + line.trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */

        }
    }

    public Cursor getTitle() { return query(null, null, PROJECTION); }
    public Cursor getTitle(String t) {
        return query(
                TITLE + " like '%" + t + "%'",
                null,
                PROJECTION
        );
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