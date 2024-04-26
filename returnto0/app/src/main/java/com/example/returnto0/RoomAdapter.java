package com.example.returnto0;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.returnto0.room.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> rooms;

    // Constructor to initialize the adapter with the list of rooms
    public RoomAdapter(List<Room> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item (room) in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        // Bind data for each room item
        Room room = rooms.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        // Return the total number of rooms
        return rooms.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView textRoomName;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textRoomName = itemView.findViewById(R.id.text_room_name);
        }

        public void bind(Room room) {
            textRoomName.setText(room.getName());
        }
    }
}