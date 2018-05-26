package com.hfad.myresidentadmin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.myresidentadmin.Interface.ItemClickListener;
import com.hfad.myresidentadmin.Model.SOSHISTORY;
import com.hfad.myresidentadmin.ViewHolder.SosHistoryViewHolder;

public class Sos_History extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<SOSHISTORY,SosHistoryViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference history;



    String STATUS;

    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos__history);


        //Firebase
        db = FirebaseDatabase.getInstance();
        history = db.getReference("Sos_History");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.sosHistory);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadHistory(); //load all SOS History

        toolbar = getSupportActionBar();

    }


    private void loadHistory(){

        adapter = new FirebaseRecyclerAdapter<SOSHISTORY,SosHistoryViewHolder>(
                SOSHISTORY.class,
                R.layout.sos_history_layout,
                SosHistoryViewHolder.class,
                history
        ) {
            @Override
            protected void populateViewHolder(SosHistoryViewHolder viewHolder, final SOSHISTORY model, int position) {

                viewHolder.txtSosHistoryID.setText("SOS_History_ID: #"+adapter.getRef(position).getKey());
                viewHolder.txtSosHistoryUser.setText("User: "+model.getUser());
                viewHolder.txtSosHistoryUnit.setText("Unit: "+model.getUnit());
                viewHolder.txtSosHistoryRequestDateTime.setText("SOS Request DateTime: "+model.getRequestDateTime());
                viewHolder.txtSosHistoryDisableDateTime.setText("SOS Disable DateTime: "+model.getDisableDateTime());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Just Implement it to fix Crash when Click to this item

                        Toast.makeText(Sos_History.this, "History SHow",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }




}
