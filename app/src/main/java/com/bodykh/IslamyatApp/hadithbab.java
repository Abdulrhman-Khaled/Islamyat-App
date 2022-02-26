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

public class hadithbab extends AppCompatActivity {

    DatabaseHelper mDBHelper;
    ListView babName;
    private SQLiteDatabase mDb;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadithbab);
        setTitle("أَبْوَاب الْحَدِيثِ الشَّرِيفِ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        babName = findViewById(R.id.bab_name);
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

        ArrayList hadithBabList = new ArrayList();
        Cursor resar = mDBHelper.getReadableDatabase().rawQuery("select * from ahadith", null);
        resar.moveToFirst();
        while (resar.isAfterLast() == false) {
            hadithBabList.add(resar.getString(resar.getColumnIndex("Title")));
            resar.moveToNext();
        }
        String[] hadithBabNameArr = new String[hadithBabList.size()];
        hadithBabNameArr = (String[]) hadithBabList.toArray(hadithBabNameArr);

        final ArrayList hadithList = new ArrayList();
        Cursor rest = mDBHelper.getReadableDatabase().rawQuery("select * from ahadith", null);
        rest.moveToFirst();
        while (rest.isAfterLast() == false) {
            hadithList.add(rest.getString(rest.getColumnIndex("Body")));
            rest.moveToNext();
        }
        String[] hadithArr = new String[hadithList.size()];
        hadithArr = (String[]) hadithList.toArray(hadithArr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_custom, hadithBabNameArr);
        babName.setAdapter(adapter);

        final String[] finalHadithBabNameArr = hadithBabNameArr;
        final String[] finalHadithArr = hadithArr;
        babName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(buttonClick);
                Intent intent = new Intent(hadithbab.this, hadithbabAdp.class);
                intent.putExtra("names", finalHadithBabNameArr[i]);
                intent.putExtra("body", finalHadithArr[i]);
                startActivity(intent);
            }
        });
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