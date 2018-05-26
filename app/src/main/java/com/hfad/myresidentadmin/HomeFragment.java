package com.hfad.myresidentadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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




    TextView name,Outstanding_Bal;


    SharedPreferences mySharedPreferences;

    String filename = "myfile.txt";


    String readContent = "";
    InputStream inputStream;

    FirebaseDatabase db;
    DatabaseReference users;



    String Name, Unit, Level, No, Outstanding;


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




        name = (TextView) view.findViewById(R.id.name_home);
        Outstanding_Bal = (TextView) view.findViewById(R.id.Outstanding_Bal);


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
                    Outstanding = user.getOutstanding();




                    //Toast.makeText(getActivity(),"Welcome , "+Name,Toast.LENGTH_SHORT).show();

                    name.setText(Name);
                    Outstanding_Bal.setText("Outstanding: RM"+Outstanding);

                }else {



                    Toast.makeText(getActivity(),"User not exist in Database",Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        CardView AddUser = (CardView) view.findViewById(R.id.SignUp);
        CardView SosHistory = (CardView) view.findViewById(R.id.SOS_History);
        CardView SosRequest = (CardView) view.findViewById(R.id.SOS_Request);

        CardView Payment = (CardView) view.findViewById(R.id.payment);



        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Register Member!",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeFragment.this.getActivity(), SignUp.class); //Fragment to Activity Intent
                startActivity(intent);

            }
        });



        SosHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "SOS History Activity!",Toast.LENGTH_SHORT).show();

                Intent SOSHistory = new Intent(HomeFragment.this.getActivity(), Sos_History.class); //Fragment to Activity Intent
                startActivity(SOSHistory);

            }
        });


        SosRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "SOS Request Page!",Toast.LENGTH_SHORT).show();

                Intent SOSRequest = new Intent(HomeFragment.this.getActivity(), Sos_Request.class); //Fragment to Activity Intent
                startActivity(SOSRequest);

            }
        });

        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Payment Page!",Toast.LENGTH_SHORT).show();

                Intent PAYMENT = new Intent(HomeFragment.this.getActivity(), Payment.class); //Fragment to Activity Intent
                startActivity(PAYMENT);

            }
        });



//During Slide Down Title Bar Effect
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar);
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("My Residence APP");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
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
