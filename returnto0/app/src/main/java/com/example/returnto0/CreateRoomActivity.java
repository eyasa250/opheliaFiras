package com.example.returnto0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.returnto0.room.Room;
import com.example.returnto0.room.RoomService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText editTextRoomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        editTextRoomName = findViewById(R.id.editTextRoomName);
        Button buttonCreateRoom = findViewById(R.id.buttonCreateRoom);

        buttonCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = editTextRoomName.getText().toString().trim();
                if (!roomName.isEmpty()) {
                    // Call API to create room
                    createRoom(roomName);
                } else {
                    Toast.makeText(CreateRoomActivity.this, "Please enter room name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createRoom(String roomName) {
        RoomService apiService = RetrofitClient.getClient("http://10.0.2.2:5000").create(RoomService.class);
        Call<Room> call = apiService.createRoom(new Room(roomName));
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Room room = response.body();
                    // Room created successfully, handle response
                    Toast.makeText(CreateRoomActivity.this, "Room created: " + room.getName(), Toast.LENGTH_SHORT).show();
                    finish(); // Finish the activity after creating the room
                } else {
                    // Handle error response
                    Toast.makeText(CreateRoomActivity.this, "Failed to create room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                // Handle failure (network error, etc.)
                Toast.makeText(CreateRoomActivity.this, "Failed to create room: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
