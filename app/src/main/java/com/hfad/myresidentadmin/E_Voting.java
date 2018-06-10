package com.hfad.myresidentadmin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class E_Voting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_voting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
