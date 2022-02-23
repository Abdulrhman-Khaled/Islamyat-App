package com.bodykh.IslamyatApp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Calendar;

public class Mainpage extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static String cityNameGetter;
    TextView year, day, month;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private ResultReceiver resultReceiver;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    static double gmod(double n, double m) {
        return ((n % m) + m) % m;
    }

    static double[] kuwaiticalendar(boolean adjust) {
        Calendar today = Calendar.getInstance();
        int adj = 0;
        if (adjust) {
            adj = 0;
        } else {
            adj = 1;
        }

        if (adjust) {
            int adjustmili = 1000 * 60 * 60 * 24 * adj;
            long todaymili = today.getTimeInMillis() + adjustmili;
            today.setTimeInMillis(todaymili);
        }
        double day = today.get(Calendar.DAY_OF_MONTH);
        double month = today.get(Calendar.MONTH);
        double year = today.get(Calendar.YEAR);

        double m = month + 1;
        double y = year;
        if (m < 3) {
            y -= 1;
            m += 12;
        }

        double a = Math.floor(y / 100.);
        double b = 2 - a + Math.floor(a / 4.);

        if (y < 1583)
            b = 0;
        if (y == 1582) {
            if (m > 10)
                b = -10;
            if (m == 10) {
                b = 0;
                if (day > 4)
                    b = -10;
            }
        }

        double jd = Math.floor(365.25 * (y + 4716)) + Math.floor(30.6001 * (m + 1)) + day
                + b - 1524;

        b = 0;
        if (jd > 2299160) {
            a = Math.floor((jd - 1867216.25) / 36524.25);
            b = 1 + a - Math.floor(a / 4.);
        }
        double bb = jd + b + 1524;
        double cc = Math.floor((bb - 122.1) / 365.25);
        double dd = Math.floor(365.25 * cc);
        double ee = Math.floor((bb - dd) / 30.6001);
        day = (bb - dd) - Math.floor(30.6001 * ee);
        month = ee - 1;
        if (ee > 13) {
            cc += 1;
            month = ee - 13;
        }
        year = cc - 4716;

        double wd = gmod(jd + 1, 7) + 1;

        double iyear = 10631. / 30.;
        double epochastro = 1948084;
        double epochcivil = 1948085;

        double shift1 = 8.01 / 60.;

        double z = jd - epochastro;
        double cyc = Math.floor(z / 10631.);
        z = z - 10631 * cyc;
        double j = Math.floor((z - shift1) / iyear);
        double iy = 30 * cyc + j;
        z = z - Math.floor(j * iyear + shift1);
        double im = Math.floor((z + 28.5001) / 29.5);
        if (im == 13)
            im = 12;
        double id = z - Math.floor(29.5001 * im - 29);

        double[] myRes = new double[8];

        myRes[0] = day; // calculated day (CE)
        myRes[1] = month - 1; // calculated month (CE)
        myRes[2] = year; // calculated year (CE)
        myRes[3] = jd - 1; // julian day number
        myRes[4] = wd - 1; // weekday number
        myRes[5] = id; // islamic date
        myRes[6] = im - 1; // islamic month
        myRes[7] = iy; // islamic year

        return myRes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        setTitle("Islamyat - إسْلَاميات");
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
        String[] iMonthNames = {"مُحَرَّم", "صَفَر", "رَبِيعٍ الْأَوَّلِ", "رَبِيعٍ الثَّانِي", "جمادي الْأَوَّل", "جمادي الثَّانِي", "رَجَب", "شَعْبَان", "رَمَضَان", "شَوَّال", "ذُو الْقِعْدَةِ", "ذُو الْحِجَّةِ"};
        // This Value is used to give the correct day +- 1 day
        boolean dayTest = true;
        double[] iDate = kuwaiticalendar(dayTest);
        int isday = (int) iDate[5];
        String ismonth = iMonthNames[(int) iDate[6]];
        int isyear = (int) iDate[7];
        year = findViewById(R.id.year);
        day = findViewById(R.id.mday);
        month = findViewById(R.id.month);
        year.setText(isyear + "هـ");
        month.setText(ismonth);
        day.setText(isday + "");

        resultReceiver = new AddressResultReceiver(new Handler());

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Mainpage.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void aboutme_menu(MenuItem item) {
        Dialog dialog = new Dialog(Mainpage.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.aboutme);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void sadka_menu(MenuItem item) {
        Dialog dialog = new Dialog(Mainpage.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.sadkaa);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void facebookbut(View view) {
        Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/abdalrahman.khaled.54"));
        startActivity(i1);
    }

    public void whatsappbut(View view) {
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/201148472090"));
        startActivity(i2);
    }

    public void instagrambut(View view) {
        Intent i3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/bodykh_/"));
        startActivity(i3);
    }

    public void linkedinbut(View view) {
        Intent i4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/abdulrhman-khaled-91a3b821a/"));
        startActivity(i4);
    }

    public void gitHubbut(View view) {
        Intent i5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bodykh"));
        startActivity(i5);
    }

    public void button_Salah(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, salahTimes.class);
        startActivity(intent);
    }

    public void button_tasks(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, EverDayTasks.class);
        startActivity(intent);
    }

    public void button_quraan(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, quran.class);
        startActivity(intent);
    }

    public void button_sb7aa(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, sb7aa.class);
        startActivity(intent);
    }

    public void button_7adees(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, hadith.class);
        startActivity(intent);
    }

    public void button_qipla(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, qibla.class);
        startActivity(intent);
    }

    public void button_stories(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, stories.class);
        startActivity(intent);
    }

    public void button_adiaawazkaar(View v) {
        v.startAnimation(buttonClick);
        Intent intent = new Intent(Mainpage.this, adiaawazkaar.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(Mainpage.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Mainpage.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();

                            Location location = new Location("providerNA");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            fetchAddressFromLatLong(location);
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void fetchAddressFromLatLong(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {
                cityNameGetter = resultData.getString(Constants.RESULT_DATA_KEY);

            } else {
                Toast.makeText(Mainpage.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
        }
    }

}