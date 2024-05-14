package com.example.taskmanagerapp;



public class Task {

    private int id;
    private String title;
    private String description;
    private String dueDate;

    public Task(int id, String title, String description, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
    // Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }
    // Setter method for 'id'
    public void setId(int id) {
        this.id = id;
    }
}
