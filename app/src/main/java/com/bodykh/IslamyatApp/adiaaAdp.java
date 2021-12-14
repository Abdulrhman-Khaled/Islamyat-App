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

public class adiaaAdp extends AppCompatActivity {


    TextView adiaaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiaa_adp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        String adiaaname = getIntent().getStringExtra("name");
        String adiaatext = getIntent().getStringExtra("text");
        setTitle(adiaaname);
        adiaaText = findViewById(R.id.adiaaadp);
        adiaaText.setText(adiaatext);
        SharedPreferences preferences = getSharedPreferences("text0", MODE_PRIVATE);
        int returned_size = preferences.getInt("text1", 30);
        adiaaText.setTextSize(returned_size);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adiaa_menu, menu);
        return true;
    }

    public void changetxtsize(MenuItem item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(adiaaAdp.this);
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
                        adiaaText.setTextSize(25);
                        break;
                    case 1:
                        SharedPreferences.Editor editor2 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor2.putInt("text1", 30);
                        editor2.apply();
                        adiaaText.setTextSize(30);
                        break;
                    case 2:
                        SharedPreferences.Editor editor3 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor3.putInt("text1", 35);
                        editor3.apply();
                        adiaaText.setTextSize(35);
                        break;
                    case 3:
                        SharedPreferences.Editor editor4 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor4.putInt("text1", 45);
                        editor4.apply();
                        adiaaText.setTextSize(45);
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