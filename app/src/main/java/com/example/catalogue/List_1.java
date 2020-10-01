package com.example.catalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class List_1 extends AppCompatActivity implements songAdapter.OnItemClickListener {

    Button addSong;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newSongPopup_name,newSongPopup_artist;
    private Button newSongPopup_save,newSongPopup_cancel;
    DatabaseReference reff;
    FirebaseStorage mStorage;
    Song song;

    RecyclerView recyclerView;
    songAdapter songadapter;
    DatabaseReference ref,mDatabaseRef;
    FirebaseDatabase database;
    List<Song> mSong;
    songAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_1);

        recyclerView = (RecyclerView)findViewById(R.id.list_1_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSong = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Song");
        mAdapter = new songAdapter(List_1.this,mSong);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(List_1.this);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mSong.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Song song = postSnapshot.getValue(Song.class);
                    song.setId(postSnapshot.getKey());
                    mSong.add(song);

                }
                mAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(List_1.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemClickListener(List_1.this);


    }


    @Override
    protected void onStart() {
       super.onStart();
       ;
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
                String id = reff.push().getKey();
                song.setId(id);
                song.setSongName(newSongPopup_name.getText().toString().trim());
                song.setArtist(newSongPopup_artist.getText().toString().trim());
                reff.child(id).setValue(song);
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

    @Override
    public void onItemClick(int position) {


    }

    @Override
    public void onWhateverClick(int position) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View songPopupView = getLayoutInflater().inflate(R.layout.song_popup ,null);

        newSongPopup_name = (EditText)songPopupView.findViewById(R.id.newSongName);
        newSongPopup_artist = (EditText)songPopupView.findViewById(R.id.newSongArtist);
        Song selectedSong = mSong.get(position);
        final String selectedKey =    selectedSong.getId();
        String selectedSongName = selectedSong.getSongName();
        String selectedArtist = selectedSong.getArtist();

        newSongPopup_name.setText(selectedSongName);
        newSongPopup_artist.setText(selectedArtist);

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
                reff.child(selectedKey).setValue(song);
                Toast.makeText(List_1.this, "Edit Saved Successfully", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onDeleteClick(int position) {
        Song selectedSong = mSong.get(position);
        String selectedKey =    selectedSong.getId();
        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(this, "Song Removed", Toast.LENGTH_SHORT).show();
    }
}