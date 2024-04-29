package com.example.returnto0.ui.todolist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.returnto0.OnItemClickListener;
import com.example.returnto0.R;
import com.example.returnto0.room.Room;

import java.util.List;

import retrofit2.Callback;

// RoomAdapter.java
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private OnItemClickListener listener;
    private List<Room> roomList;

    // Constructor to initialize the dataset
    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Setter method for the listener


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single item in the RecyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.bind(room);
        Log.d("RoomAdapter", "onBindViewHolder: Binding room at position " + position + " with name: " + room.getName());
    }

    @Override
    public int getItemCount() {
        Log.d("RoomAdapter", "getItemCount: Total items in the dataset = " + roomList.size());
        return roomList.size();
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    // ViewHolder class
    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView roomNameTextView;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNameTextView = itemView.findViewById(R.id.text_room_name);

            // Set click listener on the entire item view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Check if the listener is not null before invoking its method
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Room room = roomList.get(position);
                    listener.onItemClick(room);

                    // Add console log to verify the clicked item
                    Log.d("RoomClicked", "Clicked item: " + room.getName());
                }
            }
        }

        public void bind(Room room) {
            roomNameTextView.setText(room.getName());
        }
    }
}