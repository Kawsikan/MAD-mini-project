package com.example.catalogue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        //button to add new task
        // button = view.findViewById(R.id.button3);

        //   button.setOnClickListener(new View.OnClickListener() {
        //      @Override
        //     public void onClick(View view) {
        //        startActivity(new Intent(getActivity(),AddTaskActivity.class));
        //     }
        //   });

        recyclerView = view.findViewById(R.id.recyclerViewTask);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
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
                .setQuery(taskDb, Task.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i, @NonNull Task task) {
                taskViewHolder.text_taskTitle.setText(task.getTaskTitle());
                taskViewHolder.text_taskDescription.setText(task.getTaskDescription());
                taskViewHolder.text_taskDate.setText(task.getDate());
                taskViewHolder.text_taskTime.setText(task.getTime());
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task_row, parent, false);
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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Update")) {
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        } else if (item.getTitle().equals("Delete")) {
            deleteTask(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteTask(String key) {
        taskDb.child(key).removeValue();
    }

    private void showUpdateDialog(final String key, Task item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update");
        builder.setMessage("Please update the fields");

        View update_layout = LayoutInflater.from(getActivity()).inflate(R.layout.customtask_layout, null);

        final EditText edit_update_taskTitle = update_layout.findViewById(R.id.edit_update_taskTitle);
        final EditText edit_update_taskDescription = update_layout.findViewById(R.id.edit_update_taskDescription);
        final EditText edit_update_taskDate = update_layout.findViewById(R.id.edit_update_taskDate);
        final EditText edit_update_taskTime = update_layout.findViewById(R.id.edit_update_taskTime);

        edit_update_taskTitle.setText(item.getTaskTitle());
        edit_update_taskDescription.setText(item.getTaskDescription());
        edit_update_taskDate.setText(item.getDate());
        edit_update_taskTime.setText(item.getTime());

        builder.setView(update_layout);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String taskTitle = edit_update_taskTitle.getText().toString();
                String taskDescription = edit_update_taskDescription.getText().toString();
                String taskDate = edit_update_taskDate.getText().toString();
                String taskTime = edit_update_taskTime.getText().toString();

                Task task = new Task(taskTitle, taskDescription, taskDate, taskTime);
                taskDb.child(key).setValue(task);

                Toast.makeText(getActivity(), "Task is updated!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_all) {
            taskDb.removeValue();
        }
        return super.onOptionsItemSelected(item);
    }
}
