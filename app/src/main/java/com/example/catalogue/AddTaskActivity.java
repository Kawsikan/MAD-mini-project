package com.example.catalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.Task;

public class AddTaskActivity extends AppCompatActivity {

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

        button_add_task = findViewById(R.id.button_add_task);

        database =  FirebaseDatabase.getInstance();
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

    private void saveToFirebase() {
        String taskTitle = editTaskTitle.getText().toString();
        String taskDescription = editTaskDescription.getText().toString();

        if (!TextUtils.isEmpty(taskTitle) && !TextUtils.isEmpty(taskDescription)){
            Task task = new Task(taskTitle, taskDescription);
            taskDb.push().setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddTaskActivity.this, "Task is added.", Toast.LENGTH_SHORT).show();
                    editTaskTitle.setText("");
                    editTaskDescription.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "All fields should be filled.", Toast.LENGTH_SHORT).show();
        }
    }

}