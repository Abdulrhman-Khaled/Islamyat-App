package com.bodykh.IslamyatApp;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class hadithbabAdp extends AppCompatActivity {
    TextView hadithadp;
    ScrollView svhadithbab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadithbab_adp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        hadithadp = findViewById(R.id.hadithbabadp);
        svhadithbab = findViewById(R.id.svhadithbab);
        String hadithbabsname = getIntent().getStringExtra("names");
        setTitle(hadithbabsname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String hadithbabs = getIntent().getStringExtra("body");
        hadithadp.setText(hadithbabs);
        SharedPreferences preferences = getSharedPreferences("text0", MODE_PRIVATE);
        int returned_size = preferences.getInt("text1", 30);
        hadithadp.setTextSize(returned_size);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.anbiaa_menu, menu);
        final ObjectAnimator[] animator1 = new ObjectAnimator[1];
        MenuItem itemswitch = menu.findItem(R.id.autoscroll);
        itemswitch.setActionView(R.layout.use_switch);
        final Switch sw = (Switch) menu.findItem(R.id.autoscroll).getActionView().findViewById(R.id.switch2);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    animator1[0] = ObjectAnimator.ofInt(svhadithbab, "scrollY", hadithadp.getLayout().getHeight());
                    animator1[0].setInterpolator(new LinearInterpolator());
                    animator1[0].setDuration(100000);
                    animator1[0].start();
                    Toast.makeText(hadithbabAdp.this, "تم تفعيل وضع التمرير لأسفل تلقائياً", Toast.LENGTH_SHORT).show();
                } else {
                    animator1[0].cancel();
                    Toast.makeText(hadithbabAdp.this, "تم إيقاف وضع التمرير لأسفل تلقائياً", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }

    public void changetxtsizeanb(MenuItem item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(hadithbabAdp.this);
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
                        hadithadp.setTextSize(25);
                        break;
                    case 1:
                        SharedPreferences.Editor editor2 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor2.putInt("text1", 30);
                        editor2.apply();
                        hadithadp.setTextSize(30);
                        break;
                    case 2:
                        SharedPreferences.Editor editor3 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor3.putInt("text1", 35);
                        editor3.apply();
                        hadithadp.setTextSize(35);
                        break;
                    case 3:
                        SharedPreferences.Editor editor4 = getSharedPreferences("text0", MODE_PRIVATE).edit();
                        editor4.putInt("text1", 45);
                        editor4.apply();
                        hadithadp.setTextSize(45);
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