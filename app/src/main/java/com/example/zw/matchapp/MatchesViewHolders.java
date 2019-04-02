package com.example.zw.matchapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//call every single id inside the item_matches
public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView mMatchId, mMatchName;
    public ImageView mMatchImage;

    //add phone
    public TextView mMatchPhone;

    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        //mMatchId =  itemView.findViewById(R.id.Matchid);
        mMatchName =  itemView.findViewById(R.id.MatchName);
        mMatchImage =  itemView.findViewById(R.id.MatchImage);
        mMatchPhone = itemView.findViewById(R.id.MatchPhone);

    }

    @Override
    public void onClick(View view) {

    }
}
