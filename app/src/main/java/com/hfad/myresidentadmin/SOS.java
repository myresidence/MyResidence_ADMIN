package com.hfad.myresidentadmin;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.myresidentadmin.Model.SOSHISTORY;
import com.hfad.myresidentadmin.Model.User;
import com.ncorti.slidetoact.SlideToActView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.hfad.myresidentadmin.R.drawable.sos;
import static com.hfad.myresidentadmin.R.drawable.user;

public class SOS extends AppCompatActivity {


    private LinearLayout sos_page;

    SlideToActView slide;

    int secs = 20; //set Minutes

    FirebaseDatabase database;
    DatabaseReference request;
    DatabaseReference users;
    DatabaseReference history;

    String unit,date,time,level,no;

    String username;

    String status ="0";

    String UNIT;

    String readContent = "";
    InputStream inputStream;

    SharedPreferences mySharedPreferences;

    String filename = "myfile.txt";

    String ID;

    String RequestDateTime;
    String DisableDateTime;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);



        getMyPhoneNo();



        //Firebase
        database = FirebaseDatabase.getInstance();
        request = database.getReference("SOS_STATUS");
        history = database.getReference("Sos_History");
        users = database.getReference("User");


        ID = String.valueOf(System.currentTimeMillis());


        //Get User Details
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                if(dataSnapshot.child(readContent).exists()){


                    User user = dataSnapshot.child(readContent).getValue(User.class);
                    //user.setPhone(readContent);

                    username = user.getName();
                    level =user.getLevel();
                    unit = user.getUnit();
                    no = user.getNo();



                     UNIT = ""+unit+"-"+level+"-"+no;

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformatTIME = new SimpleDateFormat("h:mma");
                    SimpleDateFormat mdformatDATE = new SimpleDateFormat("dd/MM/yyyy");
                    time = "" + mdformatTIME.format(calendar.getTime());
                    date = "" + mdformatDATE.format(calendar.getTime());

                    com.hfad.myresidentadmin.Model.SOS sos = new com.hfad.myresidentadmin.Model.SOS(
                            username,UNIT,status,date,time
                    );




                    //Create SOS Request

                    request.child(ID).setValue(sos);

                }else {



                    Toast.makeText(SOS.this,"User not exist in Database",Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








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
                slide.setText("Disable SOS On: "+countdown1);
                //Log.i("seconds remaining: " ,""+(int) (millisUnilFinished * .001f) );
                if(countdown0==1){

                    //Remove SOS request
                    request.child(ID).removeValue();


                    //Add History

                    //Get User Details
                    users.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {




                            if(dataSnapshot.child(readContent).exists()){


                                User user = dataSnapshot.child(readContent).getValue(User.class);
                                //user.setPhone(readContent);

                                String  User = user.getName();
                                level =user.getLevel();
                                unit = user.getUnit();
                                no = user.getNo();



                                String Unit = ""+unit+"-"+level+"-"+no;

                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformatTIME = new SimpleDateFormat("h:mma");
                                SimpleDateFormat mdformatDATE = new SimpleDateFormat("dd/MM/yyyy");
                                String DisableTime = "" + mdformatTIME.format(calendar.getTime());
                                String DisableDate = "" + mdformatDATE.format(calendar.getTime());

                                RequestDateTime = ""+date+"-"+time;
                                DisableDateTime = ""+DisableDate+"-"+DisableTime;






                                SOSHISTORY soshistory = new SOSHISTORY(

                                        User,Unit,RequestDateTime,DisableDateTime


                                );





                                //Create SOS History

                                history.child(ID).setValue(soshistory);

                            }else {



                                Toast.makeText(SOS.this,"User not exist in Database",Toast.LENGTH_SHORT).show();
                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);


                    slide.setText(" ");
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


                //Remove Request
                request.child(ID).removeValue();




                //Add History

                //Get User Details
                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {




                        if(dataSnapshot.child(readContent).exists()){


                            User user = dataSnapshot.child(readContent).getValue(User.class);
                            //user.setPhone(readContent);

                           String  User = user.getName();
                            level =user.getLevel();
                            unit = user.getUnit();
                            no = user.getNo();



                           String Unit = ""+unit+"-"+level+"-"+no;

                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat mdformatTIME = new SimpleDateFormat("h:mma");
                            SimpleDateFormat mdformatDATE = new SimpleDateFormat("dd/MM/yyyy");
                            String DisableTime = "" + mdformatTIME.format(calendar.getTime());
                            String DisableDate = "" + mdformatDATE.format(calendar.getTime());

                            RequestDateTime = ""+date+"-"+time;
                            DisableDateTime = ""+DisableDate+"-"+DisableTime;






                            SOSHISTORY soshistory = new SOSHISTORY(

                                    User,Unit,RequestDateTime,DisableDateTime


                                    );





                            //Create SOS History

                            history.child(ID).setValue(soshistory);

                        }else {



                            Toast.makeText(SOS.this,"User not exist in Database",Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




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


            Toast.makeText(this,"Please Wait... ",Toast.LENGTH_SHORT).show();



            return true;
        }


        return super.onKeyDown(keyCode, event);
    }



    private void getMyPhoneNo(){

        try {

            inputStream = this.openFileInput(filename);



            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                readContent = stringBuilder.toString();


            }



        }catch (Exception ex){

            Log.i("Save", ex.toString());

        }

    }



}
