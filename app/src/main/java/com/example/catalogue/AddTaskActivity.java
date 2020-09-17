package com.example.catalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //let add first taskfragment to addtask activity

        TaskFragment fragment = new TaskFragment();
        FragmentManager manager = getSupportFragmentManager();

        //show the add button in addtask layout
        //manager.beginTransaction().add(R.id.addTaskLayout,fragment).commit();
    }


}