package com.hfad.myresidentadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Home_Bottom_Navigation extends AppCompatActivity {


    private static final String TAG = Home_Bottom_Navigation.class.getSimpleName();

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private ActionBar toolbar;

    private FragmentTransaction transaction;

    private FloatingActionButton fab;

    SharedPreferences mySharedPreferences;

    String filename = "myfile.txt";


    String readContent = "";
    InputStream inputStream;

    FirebaseDatabase db;
    DatabaseReference users;



    String Name, Unit, Level, No;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__bottom__navigation);

        fab = (FloatingActionButton)findViewById(R.id.fab);


        //Hide Action Bar
        getSupportActionBar().hide();






        //Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");




        mySharedPreferences = getSharedPreferences("MY PREF",MODE_PRIVATE);
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();


        toolbar = getSupportActionBar();



        getMyPhoneNo();


        toolbar.setTitle("HOME");







        // load the store fragment by default

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, new HomeFragment());
        transaction.commit();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Home_Bottom_Navigation.this);
                builder.setTitle("SOS Button Confirmation");
                builder.setMessage("Are You Confirm Select SOS Button ?");


                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent sos_btn = new Intent(Home_Bottom_Navigation.this,SOS.class);
                        startActivity(sos_btn);

                        Toast.makeText(Home_Bottom_Navigation.this, "Our Security Guard Will Reached To Your Door Step ASAP!",Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });

                final  AlertDialog alertDialog = builder.create();

                alertDialog.show();

                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);

            }
        });




        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

               if(id == R.id.home){
                    toolbar.setTitle("HOME");
                    fragment = new HomeFragment();


                }else if(id == R.id.announcement){

                   toolbar.setTitle("Announcement");
                    fragment = new AnnouncementFragment();


                }else if(id == R.id.profile){
                    toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();

                }


                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, fragment).commit();
                transaction.addToBackStack(null);


                return true;
            }
        });

    }



    private void getMyPhoneNo(){

        try {

            inputStream = openFileInput(filename);



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

                //toolbar.setTitle(readContent);

                //EditText et = (EditText) findViewById(R.id.editText);
                //et.setText(readContent);

            }



        }catch (Exception ex){

            Log.i("Save", ex.toString());

        }

    }





}
