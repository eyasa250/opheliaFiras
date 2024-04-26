package com.example.returnto0.task;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface taskService {
    @POST("/task/addTaskToRoom")
    Call<task> createTask(@Body task taskData);
}
