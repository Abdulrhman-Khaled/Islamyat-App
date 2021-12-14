package com.bodykh.IslamyatApp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class azkaarAdp extends AppCompatActivity {

    TextView azkaarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkaar_adp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String azkaarname = getIntent().getStringExtra("name");
        String azkaartext = getIntent().getStringExtra("text");
        setTitle(azkaarname);
        azkaarText = findViewById(R.id.azkaaradp);
        azkaarText.setText(azkaartext);
        SharedPreferences preferences = getSharedPreferences("text0", MODE_PRIVATE);
        int returned_size = preferences.getInt("text1", 30);
        azkaarText.setTextSize(returned_size);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adiaa_menu, menu);
        return true;
    }

    public void changetxtsize(MenuItem item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(azkaarAdp.this);
        alertDialog.setTitle("قم بتحديد حجم الخط المناسب");
        String[] items = {"صغير - Small", "متوسط - Medium", "كبير - Large", "كبير جداً - X-Large"};
        int checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        SharedPreferences.Editor editor1 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor1.putInt("text1", 25);
                        editor1.apply();
                        azkaarText.setTextSize(25);
                        break;
                    case 1:
                        SharedPreferences.Editor editor2 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor2.putInt("text1", 30);
                        editor2.apply();
                        azkaarText.setTextSize(30);
                        break;
                    case 2:
                        SharedPreferences.Editor editor3 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor3.putInt("text1", 35);
                        editor3.apply();
                        azkaarText.setTextSize(35);
                        break;
                    case 3:
                        SharedPreferences.Editor editor4 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor4.putInt("text1", 45);
                        editor4.apply();
                        azkaarText.setTextSize(45);
                        break;
                }
            }
        });
        alertDialog.setPositiveButton("حسناً", null);
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
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