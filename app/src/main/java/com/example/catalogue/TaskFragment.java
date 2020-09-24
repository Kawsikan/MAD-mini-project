package com.example.catalogue;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.Task;
import ViewHolder.TaskViewHolder;

public class TaskFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    FirebaseDatabase database;
    DatabaseReference taskDb;
    FirebaseRecyclerOptions<Task> options;
    FirebaseRecyclerAdapter<Task, TaskViewHolder> adapter;

    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task,container,false);
        //button to add new task
       // button = view.findViewById(R.id.button3);

     //   button.setOnClickListener(new View.OnClickListener() {
      //      @Override
       //     public void onClick(View view) {
               startActivity(new Intent(getActivity(),AddTaskActivity.class));
     //     }
     //   });

        recyclerView = view.findViewById(R.id.recyclerViewTask);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddTaskActivity.class));
            }
        });

        database = FirebaseDatabase.getInstance();
        taskDb = database.getReference("Task");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        showTask();
        return view;
    }

    private void showTask() {
        options = new FirebaseRecyclerOptions.Builder<Task>()
                .setQuery(taskDb,Task.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i, @NonNull Task task) {
                taskViewHolder.text_taskTitle.setText(task.getTaskTitle());
                taskViewHolder.text_taskDescription.setText(task.getTaskDescription());
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task_row,parent,false);
                return new TaskViewHolder(itemView);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
