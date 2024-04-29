package com.example.returnto0.ui.task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.returnto0.R;
import com.example.returnto0.room.Room;
import com.example.returnto0.task.Task;
import com.example.returnto0.task.TaskService;
import com.example.returnto0.RetrofitClient;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addtaskFragment extends Fragment {

    private AddtaskViewModel mViewModel;

    public static addtaskFragment newInstance() {
        return new addtaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // fetchTasks();
        return inflater.inflate(R.layout.fragment_addtask, container, false);
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("roomobject", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                try {
                    // Extract the room object from the bundle
                    Room room = (Room) result.getSerializable("ROOM_OBJECT");
                    if (room != null) {
                        // Do whatever you need to do with the room object
                        fetchTasks();
                    }
                } catch (Exception e) {
                    Log.e("addtaskFragment", "Error handling fragment result", e);
                }
            }
        });
    }
    private void fetchTasks() {
        // Create Retrofit service for tasks
        TaskService taskService = RetrofitClient.getClient().create(TaskService.class);

        // Call API to fetch tasks associated with the room ID
        Call<List<Task>> call = taskService.getAllTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Task> tasks = response.body();
                    Log.d("addtaskFragment", "Number of tasks fetched: " + tasks.size());

                    populateTaskCardViews(tasks);
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch tasks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                // Log the error message
                Log.e("addtaskFragment", "Failed to fetch tasks", t);

                // Display a Toast message with the error
                Toast.makeText(requireContext(), "Failed to fetch tasks: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void populateTaskCardViews(List<Task> tasks) {

        // Find all CardViews by their IDs
        CardView[] cardViews = {
                getView().findViewById(R.id.cardView1),
                getView().findViewById(R.id.cardView2),
                getView().findViewById(R.id.cardView3),
                getView().findViewById(R.id.cardView4),
                getView().findViewById(R.id.cardView5),
                getView().findViewById(R.id.cardView6)
        };
        TextView[] textViews = {
                getView().findViewById(R.id.textView1),
                getView().findViewById(R.id.textView2),
                getView().findViewById(R.id.textView3),
                getView().findViewById(R.id.textView4),
                getView().findViewById(R.id.textView5),
                getView().findViewById(R.id.textView6)
        };

        // Set click listeners and text for each card dynamically
        for (int i = 0; i < Math.min(tasks.size(), cardViews.length); i++) {
            Task task = tasks.get(i);
            CardView cardView = cardViews[i];
            TextView textView = textViews[i];

            // Set click listener
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("addtaskFragment", "Card clicked: " + task.getName());

                    // Navigate to addtaskFragment
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_buttombar);
                    navController.navigate(R.id.navigation_addtask);

                    // Pass data to addtaskFragment
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("TASK_OBJECT", (Serializable) task);
                    getParentFragmentManager().setFragmentResult("roomobject",bundle);
                }
            });

            // Set the text of TextView
            textView.setText(task.getName());
        }    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddtaskViewModel.class);
        // TODO: Use the ViewModel
    }
}
