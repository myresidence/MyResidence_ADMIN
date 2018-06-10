package com.hfad.myresidentadmin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.myresidentadmin.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.FileOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

public class SignIn extends AppCompatActivity {


    EditText edtPhone, edtPassword;
    Button btnSignIn;

    FirebaseDatabase db;
    DatabaseReference users;

    SweetAlertDialog pDialog;

    String Name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (FButton)findViewById(R.id.btnSignIn);

        //Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(edtPhone.getText().toString(),edtPassword.getText().toString());
            }
        });

    }


    private void signInUser(final String phone, final String password){




        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please Waiting.....");
        pDialog.setCancelable(false);
        pDialog.show();
        //Dialog Bar




        //Save Phone Data
        final String filename = "myfile.txt";


        final String localPhone = phone;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(localPhone).exists()){



                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);

                    Name = user.getName();

                    if(Boolean.parseBoolean(user.getIsStaff())) { //If isStaff == true

                        if(user.getPassword().equals(localPassword)){



                            //Save Phone Data
                            try {
                                FileOutputStream stream = openFileOutput(filename, MODE_PRIVATE);
                                stream.write(edtPhone.getText().toString().getBytes());
                                stream.close();
                            } catch (Exception ex) {
                                Log.i("Save", ex.toString());
                            }




                            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            pDialog.setTitleText("Welcome")
                                    .setContentText(Name)
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();

                                            Intent login = new Intent(SignIn.this,Home_Bottom_Navigation.class);
                                            startActivity(login);
                                            finish();
                                        }
                                    })
                                    .show();


                        }else {

                            pDialog.setTitleText("Wrong password !");
                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            //Toast.makeText(SignIn.this,"Wrong password !",Toast.LENGTH_SHORT).show();


                        }
                    }else {


                        pDialog.setTitleText("Please login with Sfaff account");
                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

                        //Toast.makeText(SignIn.this,"Please login with Sfaff account",Toast.LENGTH_SHORT).show();

                    }


                }else {


                    pDialog.setTitleText("User not exist in Database");
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    //Toast.makeText(SignIn.this,"User not exist in Database",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
