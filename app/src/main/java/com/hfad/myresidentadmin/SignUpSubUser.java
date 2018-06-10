package com.hfad.myresidentadmin;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.myresidentadmin.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

public class SignUpSubUser extends AppCompatActivity {


    MaterialEditText subPhone, subPassword,subName,subIC,subEmail,subSecureCode,subDOB,subCarPlate,subUnit;

    Spinner subGender;

    Button subSignUp;

    String PHONE,PASSWORD,NAME,IC,EMAIL,SECURECODE,DOB,CARPLATE,UNIT,NO,LEVEL,GENDER,OUTSTANDING;

    SweetAlertDialog pDialog;

    FirebaseDatabase db;
    DatabaseReference users;



    SharedPreferences mySharedPreferences;

    String filename = "myfile.txt";


    String readContent = "";
    InputStream inputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_sub_user);


        subPassword = (MaterialEditText)findViewById(R.id.SubPassword);
        subPhone = (MaterialEditText)findViewById(R.id.SubPhone);
        subName = (MaterialEditText)findViewById(R.id.SubName);
        subIC = (MaterialEditText)findViewById(R.id.SubIC);
        subEmail = (MaterialEditText)findViewById(R.id.SubEmail);
        subSecureCode = (MaterialEditText)findViewById(R.id.SubSecureCode);
        subDOB = (MaterialEditText)findViewById(R.id.SubDOB);
        subCarPlate = (MaterialEditText)findViewById(R.id.SubCarPlate);
        subUnit = (MaterialEditText)findViewById(R.id.SubUnit);

        subSignUp = (FButton)findViewById(R.id.SubSignUp);

        subGender = (Spinner)findViewById(R.id.SubGenderSpinner);




        mySharedPreferences = this.getSharedPreferences("MY PREF",MODE_PRIVATE);


        getMyPhoneNo();


        //Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");


        //Get User Details
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                if(dataSnapshot.child(readContent).exists()){


                    User user = dataSnapshot.child(readContent).getValue(User.class);
                    //user.setPhone(readContent);


                    LEVEL =user.getLevel();
                    UNIT = user.getUnit();
                    NO = user.getNo();
                    OUTSTANDING = user.getOutstanding();



                    //Toast.makeText(getActivity(),"Welcome , "+Name,Toast.LENGTH_SHORT).show();

                    subUnit.setText(UNIT+"-"+LEVEL+"-"+NO);

                }else {



                    Toast.makeText(SignUpSubUser.this,"User not exist in Database",Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Auto Generate 6 Digit Random Code
        Random r = new Random();
        int Low = 100000;
        int High = 999999;
        int Result = r.nextInt(High-Low) + Low;

        SECURECODE = Integer.toString(Result);

        subSecureCode.setText(SECURECODE);






        subDOB.setFocusable(false);

        subDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int startYear = c.get(Calendar.YEAR);
                int startMonth = c.get(Calendar.MONTH);
                int startDay = c.get(Calendar.DAY_OF_MONTH);
                DOBDateListener dl = new DOBDateListener();
                DatePickerDialog dialog = new DatePickerDialog(SignUpSubUser.this, dl, startYear, startMonth, startDay);
                DatePicker datePicker = dialog.getDatePicker();
                dialog.show();

            }
        });




        subSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CreateSubUser();

            }
        });




    }



    //Create Current User
    public void CreateSubUser(){



        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Searching.....");
        pDialog.setCancelable(false);
        pDialog.show();
        //Dialog Bar



        PASSWORD = subPassword.getText().toString();
        NAME = subName.getText().toString();
        IC = subIC.getText().toString();
        EMAIL =subEmail.getText().toString();
        SECURECODE = subSecureCode.getText().toString();
        CARPLATE = subCarPlate.getText().toString();
        GENDER = subGender.getSelectedItem().toString();
        PHONE = subPhone.getText().toString();





        if (TextUtils.isEmpty(EMAIL)) {

            subEmail.setError("Enter Email Address");
            pDialog.setTitleText("Email address Cant't Blank !");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);


        }else if (TextUtils.isEmpty(PHONE)) {

            subPhone.setError("Enter Phone Number");
            pDialog.setTitleText("Phone Number Cant't Blank !");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        }else if (TextUtils.isEmpty(PASSWORD)) {

            subPassword.setError("Enter Password");
            pDialog.setTitleText("Password Cant't Blank !");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        }else if (PASSWORD.length() < 6) {

            subPassword.setError("Enter Password");
            pDialog.setTitleText("Password too short, enter minimum 6 characters!");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        }else if (TextUtils.isEmpty(NAME)) {

            subName.setError("Enter Name!");
            pDialog.setTitleText("Name Cant't Blank !");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);


        }else if (TextUtils.isEmpty(IC)) {

            subIC.setError("Enter IC NUMBER!");
            pDialog.setTitleText("IC NUMBER Cant't Blank !");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        }else if (DOB.equals("")) {

            pDialog.setTitleText("DOB Cant't Blank !");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        }else if (TextUtils.isEmpty(CARPLATE)) {

            subCarPlate.setError("Enter CAR PLATE NUMBER!");
            pDialog.setTitleText("CAR PLATE NUMBER Cant't Blank !");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        }else if (GENDER.equals("Select Gender")) {

            subCarPlate.setError("Select Gender");
            pDialog.setTitleText("Please Select Gender");
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        }else  {


            //Init Firebase
            db = FirebaseDatabase.getInstance();
            users = db.getReference("User");

            //Check got Exiting Email or not


            users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //Check got Exiting Phone Num or Not
                    if(dataSnapshot.child(PHONE).exists()){


                        subPhone.setError("Phone Number is Exiting Please Try Other Number");
                        pDialog.setTitleText("Phone Number invalid !");
                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);



                    }else {


                        Log.d("Not Found: ", "Not Found");





                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseErrors) {

                }
            });



            users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //Check got Exiting Phone Num or Not
                    if(dataSnapshot.child(PHONE).exists()){


                        subPhone.setError("Phone Number is Exiting Please Try Other Number");
                        pDialog.setTitleText("Phone Number invalid !");
                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);



                    }else {


                        Log.d("Not Found: ", "Not Found");



                        DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-resident-app.firebaseio.com/User");



                        mRef1.orderByChild("email").equalTo(EMAIL).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                if (dataSnapshot.exists()) {

                                    Log.d("Found: ","Name is Exiting");

                                    subEmail.setError("Email is Exiting Please Try Other Email");
                                    pDialog.setTitleText("Email invalid !");
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);




                                }else {
                                    Log.d("Not Found: ", "Not Found");



                                    pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.setTitleText("Register Successful")
                                            .setContentText("Complete")
                                            .setConfirmText("Close")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();

                                                    SUCCESSFULVERIFICATION();
                                                    finish();
                                                }
                                            })
                                            .show();






                                }





                            }






                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseErrors) {

                }
            });




        }




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


                //toolbar.setTitle(readContent);

                //EditText et = (EditText) findViewById(R.id.editText);
                //et.setText(readContent);

            }



        }catch (Exception ex){

            Log.i("Save", ex.toString());

        }

    }


    public void SUCCESSFULVERIFICATION(){

        DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-resident-app.firebaseio.com/User");
        DatabaseReference mRefChild = mRef1.child(subPhone.getText().toString());
        mRefChild.child("name").setValue(subName.getText().toString());
        mRefChild.child("ic").setValue(subIC.getText().toString());
        mRefChild.child("dateOfBirth").setValue(subDOB.getText().toString());
        mRefChild.child("password").setValue(subPassword.getText().toString());
        mRefChild.child("gender").setValue(subGender.getSelectedItem().toString());
        mRefChild.child("No").setValue(NO);
        mRefChild.child("Level").setValue(LEVEL);
        mRefChild.child("Unit").setValue(UNIT);
        mRefChild.child("carPlateNo").setValue(subCarPlate.getText().toString());
        mRefChild.child("isStaff").setValue("false");
        mRefChild.child("email").setValue(subEmail.getText().toString());
        mRefChild.child("type").setValue(readContent);
        mRefChild.child("secureCode").setValue(SECURECODE);
        mRefChild.child("Outstanding").setValue(OUTSTANDING);

    }




    //Received date Picker Function
    public class DOBDateListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dob =year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

            DOB = dob;

            subDOB.setText(DOB);

        }
    }
}
