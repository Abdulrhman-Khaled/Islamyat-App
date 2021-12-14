package com.bodykh.IslamyatApp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;

public class stories extends AppCompatActivity {


    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        setTitle("Stories - قَصَص وَمُعْجِزَات");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void quraan_stories(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(stories.this, quranStories.class);
        startActivity(intent);
    }

    public void anbiaa_stories(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(stories.this, anbiaa.class);
        startActivity(intent);
    }
}