package com.bodykh.IslamyatApp;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class salahTimes extends AppCompatActivity {

    private static final String TAG = "tag";
    TextView cityname, fagr, shrouq, dohr, asr, magreb, eshaa;
    String url;
    String mIshaa, mFajr, mShrouq, mDhur, mAsr, mMaghrieb;
    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("مَوَاقِيت الصَّلَاة - Prayer Times");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        setContentView(R.layout.activity_salah_times);
        cityname = findViewById(R.id.citynameid2);
        fagr = findViewById(R.id.fagrid);
        shrouq = findViewById(R.id.shrouqid);
        dohr = findViewById(R.id.dohrid);
        asr = findViewById(R.id.asrid);
        magreb = findViewById(R.id.magrebid);
        eshaa = findViewById(R.id.eshaaid);
        cityname.setText(Mainpage.cityNameGetter);

        url = "https://muslimsalat.com/" + Mainpage.cityNameGetter + ".json?key=e2246567131ccd3ede8061c32bdb1951";
        final ProgressDialog pDialog = new ProgressDialog(salahTimes.this);
        pDialog.setMessage("جاري تحديد مواقيت الصلاة...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            mFajr = response.getJSONArray("items").getJSONObject(0).get("fajr").toString();
                            mShrouq = response.getJSONArray("items").getJSONObject(0).get("shurooq").toString();
                            mDhur = response.getJSONArray("items").getJSONObject(0).get("dhuhr").toString();
                            mAsr = response.getJSONArray("items").getJSONObject(0).get("asr").toString();
                            mMaghrieb = response.getJSONArray("items").getJSONObject(0).get("maghrib").toString();
                            mIshaa = response.getJSONArray("items").getJSONObject(0).get("isha").toString();
                            fagr.setText(mFajr);
                            shrouq.setText(mShrouq);
                            dohr.setText(mDhur);
                            asr.setText(mAsr);
                            magreb.setText(mMaghrieb);
                            eshaa.setText(mIshaa);
                            Toast.makeText(salahTimes.this, "تم تعيين مواقيت الصلاة طبقاً لموقعك بنجاح", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(salahTimes.this, "فشل في تعيين مواقيت الصلاة طبقاً لموقعك", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(salahTimes.this, "خطأ في تعيين مواقيت الصلاة برجاء التحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        /*SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        String formattedDate = dateFormat.format(new Date()).toLowerCase();
        Toast.makeText(salahTimes.this, formattedDate, Toast.LENGTH_SHORT).show();*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salah_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void refresh_menu(MenuItem item) {
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
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
