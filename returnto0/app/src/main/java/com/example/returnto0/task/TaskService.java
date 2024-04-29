package com.example.returnto0.task;

import com.example.returnto0.room.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface TaskService {
    @POST("/task/addTaskToRoom")
    Call<Task> createTask(@Body Task taskData);

    @GET("/task")
    Call<List<Task>> getAllTasks();

}
