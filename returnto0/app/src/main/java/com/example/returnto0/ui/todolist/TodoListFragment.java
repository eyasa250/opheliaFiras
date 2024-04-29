package com.example.returnto0.ui.todolist;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoListFragment extends Fragment {

    private TodoListViewModel mViewModel;
    private RoomAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Set a temporary empty adapter
        adapter = new RoomAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        RoomService roomService = RetrofitClient.getClient().create(RoomService.class);
        Call<List<Room>> call = roomService.getAllRooms();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Room> rooms = response.body();
                    Log.d("TodoListFragment", "Number of rooms fetched: " + rooms.size());
                    // Update the adapter with the retrieved data
                    adapter.setData(rooms);
                } else {
                    Log.d("TodoListFragment", "Number of rooms fetched: 0");

                    Toast.makeText(requireContext(), "Failed to fetch room ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                // Log the error message and stack trace
                Log.e("TodoListFragment", "Failed to fetch rooms", t);

                // Log additional information about the failure
                Log.e("TodoListFragment", "Call: " + call.request().url());

                // Print the stack trace to the console
                t.printStackTrace();

                // Show a Toast message to the user
                Toast.makeText(requireContext(), "Failed to fetch room data", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
