package com.bodykh.IslamyatApp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;

public class adiaawazkaar extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiaawazkaar);
        setTitle("Citation and Duas - أَدْعِيَة وَأَذْكَار");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void button_adiaa(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(adiaawazkaar.this, adiaa.class);
        startActivity(intent);
    }

    public void button_azkar(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(adiaawazkaar.this, azkaar.class);
        startActivity(intent);
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

    public void button_ro2yaa(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(adiaawazkaar.this, ro2yaa.class);
        startActivity(intent);
    }
}