package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.taskmanagerapp.MainActivity;
import androidx.appcompat.app.AppCompatActivity;
public class AddTaskActivity extends AppCompatActivity {

    TaskDbHelper dbHelper;
    private Button saveButton;
    private EditText addtitleEditText;
    private  EditText descriptionEditText;
    private  EditText dueDateEditText;
    private  Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        addtitleEditText = findViewById(R.id.addtitleEditText);
        descriptionEditText = findViewById(R.id.taskDescriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        saveButton.setOnClickListener(v -> saveTask());
        backButton.setOnClickListener(v -> backHome());
    }

    private void saveTask() {

        String title = addtitleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String dueDate = dueDateEditText.getText().toString();

        if (!DateValidation.isInputValid(title, description, dueDate)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!DateValidation.isDateValid(dueDate)) {
            Toast.makeText(this, "Please enter a valid date in the format dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return;
        }
        Task createdTask = new Task(title, description, dueDate.toString());
        TaskDbHelper dbHelper = new TaskDbHelper(this);
        long newTaskId = dbHelper.createTask(createdTask);
        createdTask.setId((int) newTaskId);

        Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        backHome();
        finish();
    }

    private void backHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


