package com.hfad.myresidentadmin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.myresidentadmin.Common.Common;
import com.hfad.myresidentadmin.Interface.ItemClickListener;
import com.hfad.myresidentadmin.Model.SOS;
import com.hfad.myresidentadmin.ViewHolder.SosViewHolder;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class Sos_Request extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<SOS,SosViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference request;

    MaterialSpinner spinner;

    String STATUS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos__request);

        //Firebase
        db = FirebaseDatabase.getInstance();
        request = db.getReference("SOS_STATUS");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadRequest(); //load all Orders


    }





    private void loadRequest(){

        adapter = new FirebaseRecyclerAdapter<SOS,SosViewHolder>(
                SOS.class,
                R.layout.sos_layout,
                SosViewHolder.class,
                request
        ) {
            @Override
            protected void populateViewHolder(SosViewHolder viewHolder, final SOS model, int position) {

                viewHolder.txtSosID.setText(adapter.getRef(position).getKey());
                viewHolder.txtSosStatus.setText(Common.convertCodeToStatus1(model.getStatus()));
                viewHolder.txtSosUser.setText(model.getUser());
                viewHolder.txtSosUnit.setText(model.getUnit());
                viewHolder.txtSosDate.setText(model.getDate());
                viewHolder.txtSosTime.setText(model.getTime());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Just Implement it to fix Crash when Click to this item

                        Toast.makeText(Sos_Request.this, "POP UP DIALOG BOX",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else if(item.getTitle().equals(Common.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());

        return super.onContextItemSelected(item);
    }




    private void showUpdateDialog (String key, final SOS item){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Sos_Request.this);
        alertDialog.setTitle("Update Status");
        alertDialog.setMessage("Please Choose Status");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_sos_layout,null);

        spinner = (MaterialSpinner)view.findViewById(R.id.statusSpinner1);
        spinner.setItems("Pending","On my way","Settle");

        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()).toString());


                request.child(localKey).setValue(item);
            }

        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        alertDialog.show();

    }



    private void deleteOrder(String key){
        request.child(key).removeValue();

    }



}
