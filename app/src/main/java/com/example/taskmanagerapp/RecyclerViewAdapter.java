package com.example.taskmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Task> tasks;
    private Context context;
    private TaskDbHelper dbHelper;
    public RecyclerViewAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
        this.dbHelper = new TaskDbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.delete_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());
        holder.dueDateTextView.setText(task.getDueDate());

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTaskActivity.class);
            intent.putExtra("TASK_ID", task.getId());
            intent.putExtra("TASK_TITLE", task.getTitle());
            intent.putExtra("TASK_DESCRIPTION", task.getDescription());
            intent.putExtra("TASK_DUE_DATE", task.getDueDate());
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            // 删除数据库中的任务
            if (dbHelper.deleteTask(task)) {
                // 从列表中移除任务
                tasks.remove(position);
                // 通知adapter数据已经改变
                notifyItemRemoved(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dueDateTextView;
        TextView descriptionTextView;
        Button editButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

