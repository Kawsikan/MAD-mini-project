package com.example.catalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class diaryUpdate extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {


    Button dateBtn,saveBtn;
    EditText diaryTitle,diaryDis;
    TextView dateTxt,date;
    DatabaseReference reff;
    Diary diary;
    String currentDateTimeString;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_update);

        dateBtn = findViewById(R.id.dateUpdateBtn);
        dateTxt = findViewById(R.id.dateUpdateTxt);
        diaryTitle = findViewById(R.id.diaryUpdateTitle);
        date = (TextView)findViewById(R.id.dateUpdateTxt);
        diaryDis = findViewById(R.id.diaryUpdateDis);
        saveBtn = findViewById(R.id.save_Update_btn);
        diary = new Diary();
        reff= FirebaseDatabase.getInstance().getReference().child("Diary");

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        diaryTitle.setText(title);
        String updateDate = intent.getStringExtra("date");
        date.setText(updateDate);
        String des = intent.getStringExtra("des");
        diaryDis.setText(des);
         key = intent.getStringExtra("key");

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog();
            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

                diary.setId(key);
                diary.setTitle(diaryTitle.getText().toString().trim());
                diary.setDate(date.getText().toString().trim());
                diary.setDescription(diaryDis.getText().toString().trim());
                diary.setTime(currentDateTimeString);
                reff.child(key).setValue(diary);

                Toast.makeText(diaryUpdate.this, "Diary Updated Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),TaskListActivity.class);
                int id = R.id.nav_diary;
                intent.putExtra("ID_V", id);
                Log.i("Lifecycle","kh"+id);
                startActivity(intent);



            }
        });
    }
    private void showDatePickerDialog(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = i + "/" + i1 + "/" + i2;
        dateTxt.setText(date);
    }
}