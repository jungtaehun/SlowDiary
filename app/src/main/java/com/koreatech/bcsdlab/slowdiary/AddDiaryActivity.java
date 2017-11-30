package com.koreatech.bcsdlab.slowdiary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddDiaryActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mContent;
    private EditText mOpenDate;
    private Button mSave;
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

}
