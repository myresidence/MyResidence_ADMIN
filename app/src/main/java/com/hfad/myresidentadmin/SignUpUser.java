package com.hfad.myresidentadmin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

public class SignUpUser extends AppCompatActivity {

    MaterialEditText UserPhone, UserPassword,UserName,UserIC,UserEmail,UserSecureCode,UserDOB,UserCarPlate;

    Spinner UserGender,UserNo,UserUnit,UserLevel;

    Button UserSignUp;

    String PHONE,PASSWORD,NAME,IC,EMAIL,SECURECODE,DOB,CARPLATE,UNIT,NO,LEVEL,GENDER,OUTSTANDING;

    SweetAlertDialog pDialog;

    FirebaseDatabase db;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);



        UserPassword = (MaterialEditText)findViewById(R.id.UserPassword);
        UserPhone = (MaterialEditText)findViewById(R.id.UserPhone);
        UserName = (MaterialEditText)findViewById(R.id.UserName);
        UserIC = (MaterialEditText)findViewById(R.id.UserIC);
        UserEmail = (MaterialEditText)findViewById(R.id.UserEmail);
        UserSecureCode = (MaterialEditText)findViewById(R.id.UserSecureCode);
        UserDOB = (MaterialEditText)findViewById(R.id.UserDOB);
        UserCarPlate = (MaterialEditText)findViewById(R.id.UserCarPlate);


        UserSignUp = (FButton)findViewById(R.id.UserSignUp);

        UserUnit = (Spinner)findViewById(R.id.UserUnit);
        UserLevel = (Spinner)findViewById(R.id.UserLevel);
        UserNo = (Spinner)findViewById(R.id.UserNo);
        UserGender = (Spinner)findViewById(R.id.UserGenderSpinner);





    }
}
