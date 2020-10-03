package com.example.catalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class diaryshow extends AppCompatActivity {

    TextView diaryTitle,diarydes,diarydate;
    Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaryshow);

        diaryTitle = findViewById(R.id.diary_show_title);
        back = findViewById(R.id.back_button);
        diarydes = findViewById(R.id.des);
        diarydate = findViewById(R.id.diary_show_date);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String des = intent.getStringExtra("des");

        diaryTitle.setText(title);
        diarydes.setText(des);
        diarydate.setText(date);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TaskListActivity.class);
                int id = R.id.nav_diary;
                intent.putExtra("ID_V", id);
                Log.i("Lifecycle","kh"+id);
                startActivity(intent);
            }
        });

    }
}