package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private Button updateButton;
    private EditText addtitleEditText;
    private  EditText descriptionEditText;
    private  EditText dueDateEditText;
    private  Button backButton;
    private TaskDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);
        dbHelper = new TaskDbHelper(this);

        addtitleEditText = findViewById(R.id.addtitleEditText);
        descriptionEditText = findViewById(R.id.taskDescriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.backButton);

        updateButton.setOnClickListener(v -> updateTask());
        backButton.setOnClickListener(v -> backHome());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TASK_ID")) {
            int taskId = intent.getIntExtra("TASK_ID", -1);
            String taskTitle = intent.getStringExtra("TASK_TITLE");
            String taskDescription = intent.getStringExtra("TASK_DESCRIPTION");
            String taskDueDate = intent.getStringExtra("TASK_DUE_DATE");

            addtitleEditText.setText(taskTitle);
            descriptionEditText.setText(taskDescription);
            dueDateEditText.setText(taskDueDate);
        }

    }



    public void updateTask() {
        String title = addtitleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String dueDate = dueDateEditText.getText().toString();

        if (!DateValidation.isInputValid(title, description, dueDate)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!DateValidation.isDateValid(dueDate)) {
            Toast.makeText(this, "Please enter a valid date in the format dd/MM/yyyy that is not today", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = getIntent();
        int taskId = intent.getIntExtra("TASK_ID", -1);
        if (intent != null && intent.hasExtra("TASK_ID")) {


            if (taskId == -1) {
                Toast.makeText(this, "Invalid task ID", Toast.LENGTH_SHORT).show();
                return;
            }


            Task task = new Task(title, description, dueDate);
            task.setId(taskId);


            if (dbHelper.updateTask(task)) {
                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
            }
            backHome();
            finish();
        }

    }

    private void backHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}

