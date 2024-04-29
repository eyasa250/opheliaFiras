package com.example.returnto0.ui.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.returnto0.R;
import com.example.returnto0.RetrofitClient;
import com.example.returnto0.room.Room;
import com.example.returnto0.room.RoomService;
import com.example.returnto0.task.Task;
import com.example.returnto0.task.TaskService;
import com.example.returnto0.ui.task.TaskAdapter;
import com.example.returnto0.ui.todolist.RoomAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskFragment extends Fragment {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        TaskService TaskService = RetrofitClient.getClient().create(TaskService.class);
        Call<List<Task>> call = TaskService.getTasks();
        Log.d("TodoListFragment", "API URL: " + call.request().url());

        taskRecyclerView = view.findViewById(R.id.task_recycler_view);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(new ArrayList<>()); // Empty list initially
        taskRecyclerView.setAdapter(taskAdapter);
        TaskService.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    List<Task> tasks = response.body();
                    Log.d("TaskFragment", "Tasks received: " + tasks.size());
                    taskAdapter.updateTasks(tasks); // Update adapter with tasks (existing code)
                } else {
                    // Handle API error
                    Log.e("TaskFragment", "Error fetching tasks: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                // Handle network error or other failures
                Log.e("TaskFragment", "Error fetching tasks", t);

                // You can display an error message to the user here
                // For example:
                Toast.makeText(getContext(), "Failed to load tasks!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void fetchTasksFromEndpoint() {
        // Replace "TasksService" with your actual API interface name

    }
}
