package com.example.catalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class List_1 extends AppCompatActivity {

    Button addSong;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newSongPopup_name,newSongPopup_artist;
    private Button newSongPopup_save,newSongPopup_cancel;
    DatabaseReference reff;
    Song song;

    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_1);

        recyclerView = findViewById(R.id.list_1_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Song");



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Song,Holder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Song, Holder>(Song.class,R.layout.data,Holder.class,ref) {
            @Override
            protected void populateViewHolder(Holder holder, Song song, int i) {

                holder.setView(getApplicationContext(),song.getSongName(),song.getArtist());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public  void addWish(View view){

        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

    }
    public void createNewSongDialog(View view){

        dialogBuilder = new AlertDialog.Builder(this);
        final View songPopupView = getLayoutInflater().inflate(R.layout.song_popup ,null);

        newSongPopup_name = (EditText)songPopupView.findViewById(R.id.newSongName);
        newSongPopup_artist = (EditText)songPopupView.findViewById(R.id.newSongArtist);

        newSongPopup_save = (Button)songPopupView.findViewById(R.id.song_save_btn);
        newSongPopup_cancel = (Button)songPopupView.findViewById(R.id.song_cancel_btn);

        dialogBuilder.setView(songPopupView);
        dialog =  dialogBuilder.create();
        dialog.show();

        song = new Song();
        reff= FirebaseDatabase.getInstance().getReference().child("Song");
        newSongPopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                song.setSongName(newSongPopup_name.getText().toString().trim());
                song.setArtist(newSongPopup_artist.getText().toString().trim());
                reff.push().setValue(song);
                Toast.makeText(List_1.this, "Song Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),List_1.class);
                startActivity(intent);
            }
        });
        newSongPopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}