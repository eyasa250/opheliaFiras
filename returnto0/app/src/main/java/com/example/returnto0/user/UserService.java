package com.example.returnto0.user;

import com.example.returnto0.ui.profile.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @GET("user/profile")
    Call<User> getUserDetails(@Header("Authorization") String token);

    @POST("/user/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @POST("/user/signup")
    Call<SignUpResponse> signup(@Body SignUpBody signUpBody);
}
