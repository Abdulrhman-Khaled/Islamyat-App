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

public class anbiaa extends AppCompatActivity {

    DatabaseHelper mDBHelper;
    ListView anbiaalist;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anbiaa);
        setTitle("مُعْجِزَاتِ الْأَنْبِيَاءِ - Prophets Miracles");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anbiaalist = findViewById(R.id.anbiaalist);
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
        ArrayList anbianame = new ArrayList();
        Cursor res = mDBHelper.getReadableDatabase().rawQuery("select * from ANBIAA", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            anbianame.add(res.getString(res.getColumnIndex("header")));
            res.moveToNext();
        }
        String[] anbianamearr = new String[anbianame.size()];
        anbianamearr = (String[]) anbianame.toArray(anbianamearr);

        /////////
        ArrayList anbiast = new ArrayList();
        Cursor rest = mDBHelper.getReadableDatabase().rawQuery("select * from ANBIAA", null);
        rest.moveToFirst();
        while (rest.isAfterLast() == false) {
            anbiast.add(rest.getString(rest.getColumnIndex("title")));
            rest.moveToNext();
        }
        String[] anbiastarr = new String[anbiast.size()];
        anbiastarr = (String[]) anbiast.toArray(anbiastarr);
        /////////////
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_custom, anbianamearr);
        anbiaalist.setAdapter(adapter);

        final String[] finalAnbianamearr = anbianamearr;
        final String[] finalAnbiastarr = anbiastarr;
        anbiaalist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(buttonClick);
                Intent intent = new Intent(anbiaa.this, anbiaa_adapter.class);
                intent.putExtra("names", finalAnbianamearr[i]);
                intent.putExtra("stories", finalAnbiastarr[i]);
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