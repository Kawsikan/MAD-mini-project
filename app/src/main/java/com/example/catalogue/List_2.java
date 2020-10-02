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


public class List_2 extends AppCompatActivity implements movieAdapter.OnItemClickListener {

    Button addSong;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newMoviePopup_name,newMoviePopup_writer;
    private Button newMoviePopup_save,newMoviePopup_cancel;
    DatabaseReference reff;
    FirebaseStorage mStorage;
    Movie movie;

    RecyclerView recyclerView;
    songAdapter songadapter;
    DatabaseReference ref,mDatabaseRef;
    FirebaseDatabase database;
    List<Movie> mMovie;
    movieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_2);

        recyclerView = (RecyclerView)findViewById(R.id.list_2_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mMovie = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Movie");
        mAdapter = new movieAdapter(List_2.this,mMovie);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(List_2.this);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mMovie.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Movie movie = postSnapshot.getValue(Movie.class);
                    movie.setId(postSnapshot.getKey());
                    mMovie.add(movie);

                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(List_2.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemClickListener(List_2.this);


    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    public void createNewMovieDialog(View view){

        dialogBuilder = new AlertDialog.Builder(this);
        final View moviePopupView = getLayoutInflater().inflate(R.layout.movie_popup ,null);

        newMoviePopup_name = (EditText)moviePopupView.findViewById(R.id.newMovieName);
        newMoviePopup_writer = (EditText)moviePopupView.findViewById(R.id.newMovieWriter);

        newMoviePopup_save = (Button)moviePopupView.findViewById(R.id.movie_save_btn);
        newMoviePopup_cancel = (Button)moviePopupView.findViewById(R.id.movie_cancel_btn);

        dialogBuilder.setView(moviePopupView);
        dialog =  dialogBuilder.create();
        dialog.show();

        movie = new Movie();
        reff= FirebaseDatabase.getInstance().getReference().child("Movie");
        newMoviePopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = reff.push().getKey();
                movie.setId(id);
                movie.setMovieName(newMoviePopup_name.getText().toString().trim());
                movie.setWriterName(newMoviePopup_writer.getText().toString().trim());
                reff.child(id).setValue(movie);
                Toast.makeText(List_2.this, "Movie Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),List_2.class);
                startActivity(intent);
            }
        });
        newMoviePopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void onItemClick(int position) {


    }


    public void onWhateverClick(int position) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View moviePopupView = getLayoutInflater().inflate(R.layout.movie_popup ,null);

        newMoviePopup_name = (EditText)moviePopupView.findViewById(R.id.newMovieName);
        newMoviePopup_writer = (EditText)moviePopupView.findViewById(R.id.newMovieWriter);
        Movie selectedMovie = mMovie.get(position);
        final String selectedKey =    selectedMovie.getId();
        String selectedMovieName = selectedMovie.getMovieName();
        String selectedWriter = selectedMovie.getWriterName();

        newMoviePopup_name.setText(selectedMovieName);
        newMoviePopup_writer.setText(selectedWriter);

        newMoviePopup_save = (Button)moviePopupView.findViewById(R.id.movie_save_btn);
        newMoviePopup_cancel = (Button)moviePopupView.findViewById(R.id.movie_cancel_btn);

        dialogBuilder.setView(moviePopupView);
        dialog =  dialogBuilder.create();
        dialog.show();

        movie = new Movie();
        reff= FirebaseDatabase.getInstance().getReference().child("Movie");
        newMoviePopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                movie.setMovieName(newMoviePopup_name.getText().toString().trim());
                movie.setWriterName(newMoviePopup_writer.getText().toString().trim());
                reff.child(selectedKey).setValue(movie);
                Toast.makeText(List_2.this, "Edit Saved Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),List_2.class);
                startActivity(intent);
            }
        });
        newMoviePopup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    public void onDeleteClick(int position) {
        Movie selectedMovie = mMovie.get(position);
        String selectedKey =    selectedMovie.getId();
        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(this, "Song Removed", Toast.LENGTH_SHORT).show();
    }

}
