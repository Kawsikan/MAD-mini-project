package com.example.catalogue;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class Wish_ListFragment extends Fragment {

    View v;
    CardView c1,c2;
    TextView txt_songs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_wishlist,container,false);
        txt_songs = v.findViewById(R.id.txt_wish_songs);
        c1 = v.findViewById(R.id.wish_songs);
        c2 = v.findViewById(R.id.wish_movies);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),List_1.class);
                Toast.makeText(getContext(), "Songs List", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),List_2.class);
                Toast.makeText(getContext(),"Movie List",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        return v;
    }
}
