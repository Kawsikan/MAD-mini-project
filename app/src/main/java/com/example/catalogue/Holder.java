package com.example.catalogue;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder{

    View view;
    public Holder(@NonNull View itemView) {
        super(itemView);

        view = itemView;

    }
    public void setView(Context context,String songName,String artist){

        TextView nameArtist = view.findViewById(R.id.nameArtist);
        TextView nameSong = view.findViewById(R.id.nameSong);

        nameSong.setText(songName);
        nameArtist.setText(artist);
    }
}
