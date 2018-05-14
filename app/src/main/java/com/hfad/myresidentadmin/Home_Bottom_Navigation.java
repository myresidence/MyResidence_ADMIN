package com.hfad.myresidentadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class Home_Bottom_Navigation extends AppCompatActivity {


    private static final String TAG = Home_Bottom_Navigation.class.getSimpleName();

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private ActionBar toolbar;

    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__bottom__navigation);


        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();


        toolbar = getSupportActionBar();

        // load the store fragment by default
        toolbar.setTitle("HOME");
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, new HomeFragment());
        transaction.commit();




        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.sos){

                    toolbar.setTitle("SOS");

                    fragment = new HomeFragment();

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




                }else if(id == R.id.home){
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




}
