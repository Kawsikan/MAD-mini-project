package com.example.catalogue;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class diaryAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button dateBtn,saveBtn;
    EditText diaryTitle,diaryDis;
    TextView dateTxt,date;
    DatabaseReference reff;
    Diary diary;
    String currentDateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        dateBtn = findViewById(R.id.dateBtn);
        dateTxt = findViewById(R.id.dateTxt);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePickerDialog();
            }
        });

        diaryTitle = findViewById(R.id.diaryTitle);
        date = (TextView)findViewById(R.id.dateTxt);
        diaryDis = findViewById(R.id.diaryDis);
        saveBtn = findViewById(R.id.save_btn);
        diary = new Diary();
        reff= FirebaseDatabase.getInstance().getReference().child("Diary");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                String ID = reff.push().getKey();
                diary.setId(ID);
                diary.setTitle(diaryTitle.getText().toString().trim());
                diary.setDate(date.getText().toString().trim());
                diary.setDescription(diaryDis.getText().toString().trim());
                diary.setTime(currentDateTimeString);
                reff.child(ID).setValue(diary);
                System.out.println("sdadadas" + currentDateTimeString);
                Toast.makeText(diaryAdd.this, "Diary Saved Successfully", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = i + "/" + i1 + "/" + i2;
        dateTxt.setText(date);
    }
}