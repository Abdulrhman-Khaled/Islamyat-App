package com.bodykh.IslamyatApp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rommansabbir.animationx.AnimationX;
import com.rommansabbir.animationx.Slide;
import com.rommansabbir.animationx.Zoom;


import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView logo, blogo;
    TextView title, title2, title3;
    //Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.color2));}
        title = findViewById(R.id.apptitle);
        title2 = findViewById(R.id.apptitle2);
        title3 = findViewById(R.id.apptitle3);
        logo = findViewById(R.id.applogo);
        blogo = findViewById(R.id.bkhlogo);
        AnimationX animationX = new AnimationX();
        animationX.setDuration(900).setAnimation(Zoom.INSTANCE.inDown(logo, animationX.getNewAnimatorSet())).start();
        animationX.setDuration(900).setAnimation(Zoom.INSTANCE.inUp(blogo, animationX.getNewAnimatorSet())).start();
        animationX.setDuration(900).setAnimation(Zoom.INSTANCE.inLeft(title, animationX.getNewAnimatorSet())).start();
        animationX.setDuration(900).setAnimation(Zoom.INSTANCE.inDown(title2, animationX.getNewAnimatorSet())).start();
        animationX.setDuration(900).setAnimation(Zoom.INSTANCE.inRight(title3, animationX.getNewAnimatorSet())).start();

        int splash_screen = 1200;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Mainpage.class);
                startActivity(intent);
                finish();
            }
        }, splash_screen);

    }
}