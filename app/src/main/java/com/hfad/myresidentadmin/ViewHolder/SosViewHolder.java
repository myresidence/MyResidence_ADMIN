package com.hfad.myresidentadmin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.hfad.myresidentadmin.Interface.ItemClickListener;
import com.hfad.myresidentadmin.R;

/**
 * Created by icefrog on 5/25/18.
 */

public class SosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txtSosID,txtSosUser,txtSosUnit,txtSosStatus,txtSosDate,txtSosTime;


    private ItemClickListener itemClickListener;

    public SosViewHolder(View itemView){
        super(itemView);


        txtSosID = (TextView)itemView.findViewById(R.id.sos_id);
        txtSosUser = (TextView)itemView.findViewById(R.id.sos_user);
        txtSosUnit = (TextView)itemView.findViewById(R.id.sos_unit);
        txtSosStatus = (TextView)itemView.findViewById(R.id.sos_status);
        txtSosDate = (TextView)itemView.findViewById(R.id.sos_request_date);
        txtSosTime = (TextView)itemView.findViewById(R.id.sos_request_time);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
        contextMenu.setHeaderTitle("Select The Action");

        contextMenu.add(0,0,getAdapterPosition(),"Update");
        contextMenu.add(0,1,getAdapterPosition(),"Delete");

    }

}