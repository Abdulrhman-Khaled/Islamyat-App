package com.bodykh.IslamyatApp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class hadithgeneral extends AppCompatActivity {
    DatabaseHelper mDBHelper;
    ListView hadithgen;
    private SQLiteDatabase mDb;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadithgeneral);
        setTitle("أَحَادِيثَ نَبَوِيَّةٍ عَامَّة");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hadithgen = findViewById(R.id.hadithgen);
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
        ////////////
        ArrayList hadithgeneral = new ArrayList();
        Cursor resar = mDBHelper.getReadableDatabase().rawQuery("select * from short_ahadith", null);
        resar.moveToFirst();
        while (resar.isAfterLast() == false) {
            hadithgeneral.add(resar.getString(resar.getColumnIndex("body")));
            resar.moveToNext();
        }
        String[] hadithgenarr = new String[hadithgeneral.size()];
        hadithgenarr = (String[]) hadithgeneral.toArray(hadithgenarr);
        //////////////
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_custom, hadithgenarr);
        hadithgen.setAdapter(adapter);
        Toast.makeText(hadithgeneral.this, "يمكنك نسخ الحديث عن طريق الضغط عليه", Toast.LENGTH_SHORT).show();

        hadithgen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(buttonClick);
                String s = (String) hadithgen.getItemAtPosition(i);
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", s);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(hadithgeneral.this, "تم نسخ الحديث بنجاح", Toast.LENGTH_SHORT).show();

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