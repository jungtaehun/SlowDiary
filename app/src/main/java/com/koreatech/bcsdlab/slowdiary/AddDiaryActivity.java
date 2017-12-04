package com.koreatech.bcsdlab.slowdiary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


public class AddDiaryActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mContent;
    private EditText mOpenDate;
    private Button mSave;
    SQLiteDatabase db;
    DatabaseOpenHelper helper;
    public static final String TITLE = "Title";
    public static final String CONTENT = "Content";
    public static final String WDATE = "WriteDate";
    public static final String ODATE = "OpenDate";
    private static final String TABLE_NAME = "TEST_TABLE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        mTitle = (EditText)findViewById(R.id.add_title);
        mContent = (EditText)findViewById(R.id.add_content);
        mOpenDate = (EditText)findViewById(R.id.add_open_date);

        mSave = (Button)findViewById(R.id.save_button);
        mSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                insert(mTitle.getText().toString(), mContent.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("Title", mTitle.getText().toString());
                intent.putExtra("Content", mContent.getText().toString());
                intent.putExtra("openDate", mOpenDate.getText().toString());
                setResult(1, intent);
                finish();
            }
        });

        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void insert(String t, String c) {
        helper = new DatabaseOpenHelper(getApplicationContext());
        db = helper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, t);
        initialValues.put(CONTENT, c);
        initialValues.put(WDATE, String.valueOf(new Date()));
        initialValues.put(ODATE, String.valueOf(new Date(2017,12,10)));
        db.insert(TABLE_NAME, null, initialValues);
    }

}
