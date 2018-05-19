package com.hfad.myresidentadmin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.myresidentadmin.Model.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {


    TextView ShowName, ShowUnit;


    SharedPreferences mySharedPreferences;

    String filename = "myfile.txt";


    String readContent = "";
    InputStream inputStream;

    FirebaseDatabase db;
    DatabaseReference users;



    String Name, Unit, Level, No;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //Init Firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        mySharedPreferences = getActivity().getSharedPreferences("MY PREF",MODE_PRIVATE);


        getMyPhoneNo();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);





        ShowName = (TextView) view.findViewById(R.id.name);
        ShowUnit  = (TextView) view.findViewById(R.id.unit_no);


        //Get User Details
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                if(dataSnapshot.child(readContent).exists()){


                    User user = dataSnapshot.child(readContent).getValue(User.class);
                    //user.setPhone(readContent);

                    Name = user.getName();
                    Level =user.getLevel();
                    Unit = user.getUnit();
                    No = user.getNo();

                    ShowName.setText("Welcome , "+Name);
                    ShowUnit.setText("Unit No: "+Unit+"-"+Level+"-"+No);

                    //Toast.makeText(getActivity(),"Successful ",Toast.LENGTH_SHORT).show();



                }else {



                    Toast.makeText(getActivity(),"User not exist in Database",Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        return view;
    }



    private void getMyPhoneNo(){

        try {

            inputStream = getActivity().openFileInput(filename);



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
