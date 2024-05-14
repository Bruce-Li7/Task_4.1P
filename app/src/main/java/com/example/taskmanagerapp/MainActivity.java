package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private TaskDbHelper dbHelper;
    private Button addTaskButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new TaskDbHelper(this);
        List<Task> tasks = dbHelper.getAllTasks();

        Collections.sort(tasks, new Comparator<Task>() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(Task task1, Task task2) {
                try {
                    return sdf.parse(task1.getDueDate()).compareTo(sdf.parse(task2.getDueDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(tasks, this);
        recyclerView.setAdapter(adapter);
        addTaskButton = findViewById(R.id.addTaskbutton);

        addTaskButton.setOnClickListener(v -> {
            // Open AddTaskActivity when the button is clicked
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }




}



