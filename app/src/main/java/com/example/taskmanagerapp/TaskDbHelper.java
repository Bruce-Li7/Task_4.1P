package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;


public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskDb.db";
    private static final int DATABASE_VERSION = 1;

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DUE_DATE = "dueDate";
    }

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " ("
                + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + TaskEntry.COLUMN_DESCRIPTION + " TEXT, "
                + TaskEntry.COLUMN_DUE_DATE + " TEXT);";

        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //create a task
    public long createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, task.getTitle());
        values.put(TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        values.put(TaskEntry.COLUMN_DUE_DATE, task.getDueDate());
        long id = db.insert(TaskEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // update task
    public boolean updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, task.getTitle());
        values.put(TaskEntry.COLUMN_DESCRIPTION, task.getDescription());
        values.put(TaskEntry.COLUMN_DUE_DATE, task.getDueDate());
        int numUpdate = db.update(TaskEntry.TABLE_NAME, values, TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
        return numUpdate > 0;
    }

    // delete task from the database
    public boolean deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numDeleted = db.delete(TaskEntry.TABLE_NAME, TaskEntry._ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
        return numDeleted > 0;
    }


    // get all the task from the database
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TaskEntry.TABLE_NAME,
                new String[]{TaskEntry._ID, TaskEntry.COLUMN_TITLE,
                        TaskEntry.COLUMN_DESCRIPTION, TaskEntry.COLUMN_DUE_DATE},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                task.setId(cursor.getInt(0));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

}
