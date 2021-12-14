package com.bodykh.IslamyatApp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class adiaa extends AppCompatActivity {
    DatabaseHelper mDBHelper;
    ListView adiaaNameList;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiaa);
        setTitle("أَدْعِيَة - Duas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        adiaaNameList = findViewById(R.id.adiaanamelist);
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
        ArrayList adiaanames = new ArrayList();
        Cursor resar = mDBHelper.getReadableDatabase().rawQuery("select * from adiaa_labels", null);
        resar.moveToFirst();
        while (resar.isAfterLast() == false) {
            adiaanames.add(resar.getString(resar.getColumnIndex("name")));
            resar.moveToNext();
        }
        String[] adiaanamesArr = new String[adiaanames.size()];
        adiaanamesArr = (String[]) adiaanames.toArray(adiaanamesArr);
        //////////////
        ArrayList adiaatext = new ArrayList();
        Cursor resen = mDBHelper.getReadableDatabase().rawQuery("select * from adiaa_labels", null);
        resen.moveToFirst();
        while (resen.isAfterLast() == false) {
            adiaatext.add(resen.getString(resen.getColumnIndex("text")));
            resen.moveToNext();
        }
        String[] adiaatextArr = new String[adiaatext.size()];
        adiaatextArr = (String[]) adiaatext.toArray(adiaatextArr);

        String[] finalAdiaanamesArr = adiaanamesArr;
        String[] finalAdiaatextArr = adiaatextArr;
        adiaaNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(buttonClick);
                Intent intent = new Intent(adiaa.this, adiaaAdp.class);
                intent.putExtra("name", finalAdiaanamesArr[i]);
                intent.putExtra("text", finalAdiaatextArr[i]);
                startActivity(intent);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_custom, adiaanamesArr);
        adiaaNameList.setAdapter(adapter);
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
