package com.example.returnto0.ui.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.returnto0.OnItemClickListener;
import com.example.returnto0.room.*;

import com.example.returnto0.R;
import com.example.returnto0.RetrofitClient;
import com.example.returnto0.room.RoomService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoListFragment extends Fragment implements OnItemClickListener {

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
        adapter = new RoomAdapter(new ArrayList<>()); // Initialize with empty list
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        Log.d("TodoListFragment", "RecyclerView: Adapter is set.");

        RoomService roomService = RetrofitClient.getClient().create(RoomService.class);
        Call<List<Room>> call = roomService.getAllRooms();
        Log.d("TodoListFragment", "API URL: " + call.request().url());

        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (!response.isSuccessful()) {
                    Log.e("TodoListFragment", "API Response Error: Code " + response.code());

                    Log.e("TodoListFragment", "Response Code: " + response.code());
                    Toast.makeText(requireContext(), "Failed to fetch room data (code: " + response.code() + ")", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Room> rooms = response.body();
                if (rooms    != null) {
                    adapter.setRoomList(rooms);
                    Log.d("TodoListFragment", "API Response: Rooms updated, count = " + rooms.size());
                } else {
                    Toast.makeText(requireContext(), "No rooms available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e("TodoListFragment", "API call failed", t);
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Room room) {
        Log.d("RoomClicked", "Room Name: " + room.getName());

        NavController navController = Navigation.findNavController(requireActivity(), R.id.navigation_todolist);

        int destinationId = R.id.navigation_addtask; // Make sure this is the correct ID of your destination fragment

        Bundle bundle = new Bundle();
        bundle.putInt("ROOM_ID", room.getId()); // Pass only the Room ID

        navController.navigate(destinationId, bundle);
    }

}