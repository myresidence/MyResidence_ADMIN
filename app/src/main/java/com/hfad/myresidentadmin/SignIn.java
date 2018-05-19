package com.hfad.myresidentadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.myresidentadmin.Common.Common;
import com.hfad.myresidentadmin.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.FileOutputStream;

import info.hoang8f.widget.FButton;

public class SignIn extends AppCompatActivity {


    EditText edtPhone, edtPassword;
    Button btnSignIn;

    FirebaseDatabase db;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

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


    private void signInUser(final String phone, String password){
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please waiting.....");
        mDialog.show();


        //Save Phone Data
        final String filename = "myfile.txt";


        final String localPhone = phone;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(localPhone).exists()){
                    mDialog.dismiss();
                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
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




                            Toast.makeText(SignIn.this,"Welcome",Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(SignIn.this,Home_Bottom_Navigation.class);
                            Common.currentUser = user;
                            startActivity(login);
                            finish();




                        }else {

                            Toast.makeText(SignIn.this,"Wrong password !",Toast.LENGTH_SHORT).show();


                        }
                    }else {
                        Toast.makeText(SignIn.this,"Please login with Sfaff account",Toast.LENGTH_SHORT).show();

                    }


                }else {


                    mDialog.dismiss();
                    Toast.makeText(SignIn.this,"User not exist in Database",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
