package com.hfad.myresidentadmin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class MaintenanceSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_support);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
