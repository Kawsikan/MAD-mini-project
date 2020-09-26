package com.example.catalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class List_1 extends AppCompatActivity {

    Button addSong;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newSongPopup_name,newSongPopup_artist;
    private Button newSongPopup_save,newSongPopup_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_1);





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

        newSongPopup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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