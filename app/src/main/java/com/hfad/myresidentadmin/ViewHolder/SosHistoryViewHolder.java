package com.hfad.myresidentadmin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.hfad.myresidentadmin.Interface.ItemClickListener;
import com.hfad.myresidentadmin.R;

/**
 * Created by icefrog on 5/26/18.
 */

public class SosHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txtSosHistoryID,txtSosHistoryUser,txtSosHistoryUnit,txtSosHistoryRequestDateTime,txtSosHistoryDisableDateTime;


    private ItemClickListener itemClickListener;

    public SosHistoryViewHolder(View itemView){
        super(itemView);


        txtSosHistoryID = (TextView)itemView.findViewById(R.id.sos_history_id);
        txtSosHistoryUser = (TextView)itemView.findViewById(R.id.sos_history_user);
        txtSosHistoryUnit = (TextView)itemView.findViewById(R.id.sos_history_unit);
        txtSosHistoryRequestDateTime = (TextView)itemView.findViewById(R.id.sos_request_datetime);
        txtSosHistoryDisableDateTime = (TextView)itemView.findViewById(R.id.sos_disable_datetime);


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