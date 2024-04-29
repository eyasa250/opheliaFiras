package com.example.returnto0.ui.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.returnto0.R;
import com.example.returnto0.room.Room;

import java.util.List;

// RoomAdapter.java
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<Room> roomList;

    // Constructor to initialize the dataset
    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }
    public void setData(List<Room> rooms) {
        roomList.clear();
        roomList.addAll(rooms);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single item in the RecyclerView
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder
        Room room = roomList.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    // ViewHolder class
    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView roomNameTextView;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            imageView = itemView.findViewById(R.id.imageView);
            roomNameTextView = itemView.findViewById(R.id.text_room_name);
        }

        // Bind data to views
        public void bind(Room room) {
            // Set data to views
            roomNameTextView.setText(room.getName());
            // You can set other data to views here
        }
    }
}
