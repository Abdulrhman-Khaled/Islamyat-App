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
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class quran extends AppCompatActivity {
    DatabaseHelper mDBHelper;
    ListView soraname;
    String[] m_or_m = {"مكية", "مدنية", "مدنية", "مدنية", "مدنية", "مكية", "مكية", "مدنية", "مدنية", "مكية", "مكية", "مكية", "مدنية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مدنية", "مكية", "مدنية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مدنية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مدنية", "مدنية", "مدنية", "مكية", "مكية", "مكية", "مكية", "مكية", "مدنية", "مكية", "مدنية", "مدنية", "مدنية", "مدنية", "مدنية", "مدنية", "مدنية", "مدنية", "مدنية", "مدنية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مدنية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مدنية", "مدنية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مكية", "مدنية", "مكية", "مكية", "مكية", "مكية"};
    private SQLiteDatabase mDb;

    /*String soranames[] = {" الْفَاتِحَةَ", " الْبَقَرَةَ", " آلَ عُمْرَانُ", " النِّسَاءَ", " الْمَائِدَةَ", " الْأَنْعَامَ", " الْأَعْرَافَ", " الْأَنْفَالَ",
            " التَّوْبَةَ", " يُونِسَ", " هُودَ", " يوسف", " الرَّعْدَ", " ابراهيم", " الْحَجَرَ", " النَّحْلَ", " الْإِسْرَاءَ", " الْكَهْفَ", " مَرْيَمَ", " طه", " الْأَنْبِيَاءَ",
            " الْحَجَّ", " الْمُؤْمِنُونَ", " النُّورَ", " الْفَرْقَانِ", " الشُّعرَاءَ", " النَّمْلَ", " الْقَصَصَ", " الْعَنْكَبُوتَ", " الرُّومَ", " لُقْمَانَ", " السَّجْدَةَ", " الْأحْزَابَ", " سبإِ", " فَاُطْرُ", " يس", " الصَّافَّاتَ", " ص", " الزَّمْرَ",
            " غَافِرَ", " فَصَلَتْ", " الشُّورَى", " الزُّخْرُفَ", " الدُّخَانَ", " الْجَاثِيَةَ", " الْأَحْقَافَ", " مُحَمَّدَ", " الْفَتْحَ", " الْحَجَرَاتِ", " ق", " الذَّارِيَاتِ", " الطَّوْرَ", " النَّجْمَ", " الْقَمَرَ", " الرَّحْمَنَ", " الْوَاقِعَةَ", " الْحَديدَ", " الْمُجَادَلَةَ", " الْحَشْرَ", " الْمُمْتَحَنَةَ", " الصَّفَّ", " الْجَمْعَةَ", " الْمُنَافِقُونَ", " التَّغَابُنَ", " الطَّلَاَقَ",
            " التَّحْرِيمَ", " الْمَلِكَ", " الْقَلَمَ", " الْحَاقَّةَ", " الْمَعَارِجَ", " نُوحِ", " الْجِنَّ", " الْمُزَّمِّلَ", " الْمُدَّثِّرَ", " الْقِيَامَةَ", " الانسان", " الْمُرْسَلَاتِ", " النبإ", " النَّازِعَاتِ", " عَبَسَ",
            " التَّكْويرَ", " الإنفطار", " الْمطففِينَ", " الإنشقاق", " الْبُروجَ", " الطَّارِقَ", " الْأعْلَى", " الْغَاشِّيَّةَ", " الْفَجْرَ", " الْبَلَدَ", " الشَّمْسَ", " اللَّيْلَ",
            " الضُّحَى", " الشَّرْحَ", " التّينَ", " الْعَلَقَ", " الْقَدْرَ", " الْبَيِّنَةَ", " الزَّلْزَلَةَ", " الْعَادِيَاتِ", " الْقَارِعَةَ", " التَّكَاثُرَ", " الْعَصْرَ", " الْهَمْزَةَ",
            " الْفِيلَ", " قُرَيْشَ", " الْمَاعُونَ", " الْكَوْثَرَ", " الْكَافِرُونَ", " النَّصْرَ", " الْمَسَدَ", " الْإِخْلَاَصَ", " الْفَلْقَ", " النَّاسَ"
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);
        setTitle("ٱلۡقُرۡآنُ ٱلۡكَرِيمُ - The Holy Quran");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        soraname = findViewById(R.id.sora_name);

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
        ////////////
        ArrayList soraarrnamearlist = new ArrayList();
        Cursor resar = mDBHelper.getReadableDatabase().rawQuery("select * from SORA_NAME", null);
        resar.moveToFirst();
        while (resar.isAfterLast() == false) {
            soraarrnamearlist.add(resar.getString(resar.getColumnIndex("sora_name_ar")));
            resar.moveToNext();
        }
        String[] soraarnamearr = new String[soraarrnamearlist.size()];
        soraarnamearr = (String[]) soraarrnamearlist.toArray(soraarnamearr);
        //////////////
        ArrayList soraennamelist = new ArrayList();
        Cursor resen = mDBHelper.getReadableDatabase().rawQuery("select * from SORA_NAME", null);
        resen.moveToFirst();
        while (resen.isAfterLast() == false) {
            soraennamelist.add(resen.getString(resen.getColumnIndex("sora_name_en")));
            resen.moveToNext();
        }
        String[] soraennamearr = new String[soraennamelist.size()];
        soraennamearr = (String[]) soraennamelist.toArray(soraennamearr);
        //////////////
        ArrayList soraarrlist = new ArrayList();
        Cursor res = mDBHelper.getReadableDatabase().rawQuery("select * from SORA_AYAT", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            soraarrlist.add(res.getString(res.getColumnIndex("sora_ayat")));
            res.moveToNext();
        }
        String[] soraarr = new String[soraarrlist.size()];
        soraarr = (String[]) soraarrlist.toArray(soraarr);
        ////////

        final String[] finalSoraennamearr = soraennamearr;
        final String[] finalSoraarr = soraarr;
        final String[] finalSoraarnamearr = soraarnamearr;
        soraname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(buttonClick);
                Intent intent = new Intent(quran.this, quraan_adpter.class);
                intent.putExtra("names", finalSoraarnamearr[i]);
                intent.putExtra("namesen", finalSoraennamearr[i]);
                intent.putExtra("soraayat", finalSoraarr[i]);
                intent.putExtra("soraamorm", m_or_m[i]);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Sora> soras = mDBHelper.getSoraName();
        final quran_info quranInfo = new quran_info(this, R.layout.list_view_quraan, soras);
        soraname.setAdapter(quranInfo);
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
