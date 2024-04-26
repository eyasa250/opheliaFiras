package com.example.returnto0.room;

import com.example.returnto0.room.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface RoomService {
    @GET("/room")
    Call<List<Room>> getAllRooms();

    @GET("/room/{id}")
    Call<Room> getRoomById(@Path("id") String id);

    @POST("/room/addroom")
    Call<Room> createRoom(@Body Room room);

    @PUT("/room/{id}")
    Call<Room> updateRoom(@Path("id") String id, @Body Room room);

    @DELETE("/room/{id}")
    Call<Void> deleteRoom(@Path("id") String id);
}
