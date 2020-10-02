package com.example.catalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.Task;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private EditText editTaskTitle, editTaskDescription;
    private Button button_add_task;

    FirebaseDatabase database;
    DatabaseReference taskDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTaskTitle = findViewById(R.id.editTaskTitle);
        editTaskDescription = findViewById(R.id.editTaskDescription);

        //Data & Time picker
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        //Data & Time picker Ends

        button_add_task = findViewById(R.id.button_add_task);

        database = FirebaseDatabase.getInstance();
        taskDb = database.getReference("Task");

        button_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToFirebase();
            }
        });

        //let add first taskfragment to addtask activity

        TaskFragment fragment = new TaskFragment();
        FragmentManager manager = getSupportFragmentManager();

        //show the add button in addtask layout
        //manager.beginTransaction().add(R.id.addTaskLayout,fragment).commit();

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }


    private void saveToFirebase() {
        String taskTitle = editTaskTitle.getText().toString();
        String taskDescription = editTaskDescription.getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();


        if (!TextUtils.isEmpty(taskTitle) && !TextUtils.isEmpty(taskDescription) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time)) {
            Task task = new Task(taskTitle, taskDescription, date, time);
            taskDb.push().setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddTaskActivity.this, "Task is added.", Toast.LENGTH_SHORT).show();
                    editTaskTitle.setText("");
                    editTaskDescription.setText("");
                    txtDate.setText("");
                    txtTime.setText("");
                    //Code added on 25th to go back to task fragment
                    //Intent intent = new Intent(getParent(),TaskFragment.class);
                    //startActivity(getIntent());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "All fields should be filled.", Toast.LENGTH_SHORT).show();
        }


    }

}