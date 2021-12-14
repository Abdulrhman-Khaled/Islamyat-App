package com.bodykh.IslamyatApp;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class ro2yaa extends AppCompatActivity {

    DatabaseHelper mDBHelper;
    ListView ro2yaali;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ro2yaa);
        setTitle("الرُّقية الشَّرْعِيَّةِ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ro2yaali = findViewById(R.id.ro2yaalist);

        //database
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        ArrayList ro2yaa = new ArrayList();
        Cursor resar = mDBHelper.getReadableDatabase().rawQuery("select * from ro2yaa", null);
        resar.moveToFirst();
        while (resar.isAfterLast() == false) {
            ro2yaa.add(resar.getString(resar.getColumnIndex("text")));
            resar.moveToNext();
        }
        String[] ro2yaaArr = new String[ro2yaa.size()];
        ro2yaaArr = (String[]) ro2yaa.toArray(ro2yaaArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_custom, ro2yaaArr);
        ro2yaali.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                recreate();

        }
        return super.onOptionsItemSelected(item);
    }
}