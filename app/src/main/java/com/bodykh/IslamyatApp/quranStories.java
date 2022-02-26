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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class quranStories extends AppCompatActivity {

    DatabaseHelper mDBHelper;
    ListView quranStoriesList;
    private SQLiteDatabase mDb;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_stories);
        setTitle("Quran Stories - قَصَص الْقُرْآن");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        quranStoriesList = findViewById(R.id.quraanstorieslist);
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
        ///////
        ArrayList storyname = new ArrayList();
        Cursor res = mDBHelper.getReadableDatabase().rawQuery("select * from quranstory", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            storyname.add(res.getString(res.getColumnIndex("header")));
            res.moveToNext();
        }
        String[] storynamearr = new String[storyname.size()];
        storynamearr = (String[]) storyname.toArray(storynamearr);

        /////////
        ArrayList quraanstory = new ArrayList();
        Cursor rest = mDBHelper.getReadableDatabase().rawQuery("select * from quranstory", null);
        rest.moveToFirst();
        while (rest.isAfterLast() == false) {
            quraanstory.add(rest.getString(rest.getColumnIndex("title")));
            rest.moveToNext();
        }
        String[] quranstoryarr = new String[quraanstory.size()];
        quranstoryarr = (String[]) quraanstory.toArray(quranstoryarr);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_custom, storynamearr);
        quranStoriesList.setAdapter(adapter);

        String[] finalStorynamearr = storynamearr;
        String[] finalQuranstoryarr = quranstoryarr;
        quranStoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(quranStories.this, quranStoriesadp.class);
                intent.putExtra("names", finalStorynamearr[i]);
                intent.putExtra("stories", finalQuranstoryarr[i]);
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