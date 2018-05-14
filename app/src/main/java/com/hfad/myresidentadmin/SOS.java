package com.hfad.myresidentadmin;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ncorti.slidetoact.SlideToActView;

public class SOS extends AppCompatActivity {


    private LinearLayout sos_page;

    SlideToActView slide;

    int secs = 6;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        sos_page = (LinearLayout) findViewById(R.id.sos_page);
        slide = (SlideToActView) findViewById(R.id.example_gray_on_green);
        //slide.setLocked(true);
        BlinkBlink();

        slide.setText("HELLO");

        new CountDownTimer((secs + 1) * 1000, 1000){

            @Override
            public final void onTick(final long millisUnilFinished)
            {
                int countdown0 = (int)(millisUnilFinished * .001f);

                String countdown1 = String.valueOf((int)(millisUnilFinished * .001f));
                slide.setText(countdown1);
                Log.i("seconds remaining: " ,""+(int) (millisUnilFinished * .001f) );
                if(countdown0==1){
                    slide.setText("BYE !!!");
                    finish();

                }

            }

            @Override
            public final void onFinish()
            {

            }


        }.start();





        slide.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView view) {
                Toast.makeText(SOS.this, "SOS Button is Canceled !", Toast.LENGTH_SHORT).show();
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                finish();

            }
        });

    }



    private void BlinkBlink() {
        ObjectAnimator anim = ObjectAnimator.ofInt(sos_page, "backgroundColor", Color.WHITE, Color.RED,
                Color.WHITE);
        anim.setDuration(1500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            Toast.makeText(this,"Please Wait... ;)",Toast.LENGTH_SHORT).show();



            return true;
        }


        return super.onKeyDown(keyCode, event);
    }



}
