package com.bodykh.IslamyatApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class sb7aa extends AppCompatActivity {

    public static final String SHARED_PREF = "shared";
    public static final String TEXT = "text";
    public int scounter;
    public String text;
    public AlphaAnimation buttonClick = new AlphaAnimation(0.8F, 0.8F);
    TextView counter;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout clickplus1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb7aa);
        setTitle("Rosary - المِسْبَحَةُ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));
        }
        counter = findViewById(R.id.counter);
        clickplus1 = findViewById(R.id.clickplus1);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        clickplus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scounter = Integer.parseInt(counter.getText().toString());
                scounter = scounter + 1;
                counter.setText(Integer.toString(scounter));
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, counter.getText().toString());
                editor.apply();
                if (scounter == 999) {
                    Toast.makeText(sb7aa.this,
                            "وصلت المسبحة للحد الاقصي بارك الله فيك برجاء اعادة تعيين المسبحة لاعادة التسبيح", Toast.LENGTH_LONG).show();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new AlertDialog.Builder(sb7aa.this)
                        .setTitle("إعَادَة تَعْيِين")
                        .setMessage("هل تريد اعادة المسبحة الي الصفر؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                counter.setText("0");
                                scounter = Integer.parseInt(counter.getText().toString());
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(TEXT, counter.getText().toString());
                                editor.apply();
                            }
                        })
                        .setNegativeButton("لا", null)
                        .show();
            }
        });
        update();
    }

    private void update() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "0");
        counter.setText(text);
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