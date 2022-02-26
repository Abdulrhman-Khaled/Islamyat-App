package com.bodykh.IslamyatApp;

import android.annotation.SuppressLint;
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

public class azkaar extends AppCompatActivity {

    DatabaseHelper mDBHelper;
    ListView azkaarNameList;
    private SQLiteDatabase mDb;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkaar);
        setTitle("أَذْكَار - Citation");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        azkaarNameList = findViewById(R.id.azkaanamelist);
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
        ArrayList azkaarnames = new ArrayList();
        Cursor resar = mDBHelper.getReadableDatabase().rawQuery("select * from azkar_labels", null);
        resar.moveToFirst();
        while (resar.isAfterLast() == false) {
            azkaarnames.add(resar.getString(resar.getColumnIndex("name")));
            resar.moveToNext();
        }
        String[] azkaarnamesArr = new String[azkaarnames.size()];
        azkaarnamesArr = (String[]) azkaarnames.toArray(azkaarnamesArr);
        //////////////
        ArrayList azkaartext = new ArrayList();
        Cursor resen = mDBHelper.getReadableDatabase().rawQuery("select * from azkar_labels", null);
        resen.moveToFirst();
        while (resen.isAfterLast() == false) {
            azkaartext.add(resen.getString(resen.getColumnIndex("text")));
            resen.moveToNext();
        }
        String[] azkaartextArr = new String[azkaartext.size()];
        azkaartextArr = (String[]) azkaartext.toArray(azkaartextArr);

        String[] finalAzkaartextArr = azkaartextArr;
        String[] finalAzkaarnamesArr = azkaarnamesArr;
        azkaarNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(buttonClick);
                Intent intent = new Intent(azkaar.this, azkaarAdp.class);
                intent.putExtra("name", finalAzkaarnamesArr[i]);
                intent.putExtra("text", finalAzkaartextArr[i]);
                startActivity(intent);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_custom, azkaarnamesArr);
        azkaarNameList.setAdapter(adapter);

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