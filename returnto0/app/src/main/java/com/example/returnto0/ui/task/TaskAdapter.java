package com.example.returnto0.ui.task;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.returnto0.R;
import com.example.returnto0.task.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void updateTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged(); // Update UI with new data
    }

    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.titleTextView.setText(task.getName());
        Log.d("TaskAdapter", "Task title: " + task.getName());  // Log task title

        // Check if checkbox is displayed (assuming its ID is "task_checkbox")
        boolean isChecked = holder.checkBox.isChecked();
        Log.d("TaskAdapter", "Checkbox at position " + position + " is checked: " + isChecked);

        // Checkbox can be used for future functionality if needed (currently unchecked)
        holder.checkBox.setChecked(false); // Set checkbox to unchecked state (optional)
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.task_title);
            checkBox = itemView.findViewById(R.id.task_checkbox);
        }
    }
}

