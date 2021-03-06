package com.koreatech.bcsdlab.slowdiary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import android.app.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class AddDiaryActivity extends AppCompatActivity{
    private EditText mTitle;
    private EditText mContent;
    private EditText mOpenDate;
    private Button mSave;
    private Button mDateBtn;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int mYear, mMonth, mDay ;
    DatePicker datePicker;

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
        /*
        toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        /* Todo AddDiaryActivity의 레이아웃과 색을 변경해야할 것 같습니다. */
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        mTitle = (EditText)findViewById(R.id.add_title);
        mContent = (EditText)findViewById(R.id.add_content);
        //mOpenDate = (EditText)findViewById(R.id.add_open_date);
        //mDateBtn = (Button)findViewById(R.id.date_btn);

        //calendar = Calendar.getInstance();

        //mYear = calendar.get(Calendar.YEAR) ;
        //mMonth = calendar.get(Calendar.MONTH);
        //mDay = calendar.get(Calendar.DAY_OF_MONTH);
/*
        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDiaryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int y,
                                                  int m, int d) {
                                mYear = y;
                                mMonth = m;
                                mDay = d;
                                String date = "Selected Date : " + mDay + "-" + mMonth + "-" + mYear;
                                Toast.makeText(AddDiaryActivity.this, date, Toast.LENGTH_LONG).show();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
*/
        mSave = (Button)findViewById(R.id.save_button);
        mSave.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unused")
            public void onClick(View view) {
                insert(mTitle.getText().toString(), mContent.getText().toString(),datePicker.getYear() ,datePicker.getMonth() ,datePicker.getDayOfMonth());
                Intent intent = new Intent();
                intent.putExtra("Title", mTitle.getText().toString());
                intent.putExtra("Content", mContent.getText().toString());
                setResult(1, intent);
                finish();
            }
        });

        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
/*
    @Override
    public void onDateSet(DatePickerDialog view, int y, int m, int d) {
        mYear = y;
        mMonth = m;
        mDay = d;
        String date = "Selected Date : " + mDay + "-" + mMonth + "-" + mYear;
        Toast.makeText(AddDiaryActivity.this, date, Toast.LENGTH_LONG).show();
    }
*/
    public void insert(String t, String c, int y, int m, int d) {
        /*

        ContentValues initialValues = new ContentValues();
            initialValues.put(TITLE, "Test Title");
            initialValues.put(CONTENT, "Test Content");
            initialValues.put(WDATE, df.format(new Date()));
            open_date = "2017-01-06 00:00:00";
            initialValues.put(ODATE, open_date);
            long id = db.insert(TABLE_NAME, null, initialValues);
         */
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        helper = new DatabaseOpenHelper(getApplicationContext());
        db = helper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, t);
        initialValues.put(CONTENT, c);
        initialValues.put(WDATE, df.format(new Date()));
        String open_date = String.valueOf(y)+"-"+String.valueOf(m)+"-"+String.valueOf(d) + " 00:00:00";
        initialValues.put(ODATE, open_date);
        db.insert(TABLE_NAME, null, initialValues);
    }

}
