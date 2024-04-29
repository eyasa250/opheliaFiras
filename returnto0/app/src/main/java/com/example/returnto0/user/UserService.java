package com.example.returnto0.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("/room")
    Call<List<User>> getAllRooms();

    @GET("/room/{id}")
    Call<User> getRoomById(@Path("id") String id);

    @POST("/room/addroom")
    Call<User> createRoom(@Body User room);

    @PUT("/room/{id}")
    Call<User> updateRoom(@Path("id") String id, @Body User room);

    @DELETE("/room/{id}")
    Call<Void> deleteRoom(@Path("id") String id);
}
