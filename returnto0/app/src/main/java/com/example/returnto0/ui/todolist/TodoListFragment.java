package com.example.returnto0.ui.todolist;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.returnto0.R;
import com.example.returnto0.RetrofitClient;
import com.example.returnto0.room.Room;
import com.example.returnto0.room.RoomService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoListFragment extends Fragment {

    private TodoListViewModel mViewModel;

    public static TodoListFragment newInstance() {
        return new TodoListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fetchRoomData();
        return inflater.inflate(R.layout.fragment_todo_list, container, false);


    }
    private void fetchRoomData() {
        RoomService apiService = RetrofitClient.getClient("http://10.0.2.2:5000").create(RoomService.class);
        Call<List<Room>> call = apiService.getAllRooms();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Room> rooms = response.body();
                    populateCardViews(rooms);
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch room data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                String errorMessage;
                if (t instanceof IOException) {
                    errorMessage = "Network error occurred: " + t.getMessage();
                } else {
                    errorMessage = "Failed to fetch room data. Unexpected error occurred: " + t.getMessage();
                }
                Log.e("Fetch Room Data Error", errorMessage, t);
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void populateCardViews(List<Room> rooms) {
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
        for (int i = 0; i < Math.min(rooms.size(), cardViews.length); i++) {
            Room room = rooms.get(i);
            CardView cardView = cardViews[i];
            TextView textView = textViews[i];

            // Set click listener
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TodoListFragment", "Card clicked: " + room.getName());

                    // Navigate to addtaskFragment
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.navigation_addtask);

                    // Pass data to addtaskFragment
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ROOM_OBJECT", room);
                    getParentFragmentManager().setFragmentResult("roomobject",bundle);
                }
            });

            // Set the text of TextView
            textView.setText(room.getName());
        }    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TodoListViewModel.class);
        // TODO: Use the ViewModel
    }

}