package com.bodykh.IslamyatApp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;

public class hadith extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadith);
        setTitle("Prophet Speech - الْأَحَادِيثِ النَّبَوِيَّةِ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hadith_bab(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(hadith.this, hadithbab.class);
        startActivity(intent);
    }

    public void hadith_general(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(hadith.this, hadithgeneral.class);
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
}