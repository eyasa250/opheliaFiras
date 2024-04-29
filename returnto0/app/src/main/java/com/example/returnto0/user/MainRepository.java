package com.example.returnto0.user;

import com.example.returnto0.RetrofitClient;
import com.example.returnto0.ui.profile.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private UserService userService;

    public MainRepository() {
        this.userService = RetrofitClient.getClient().create(UserService.class);
    }

    public void loginRemote(LoginBody loginBody, ILoginResponse loginResponse) {
        userService.login(loginBody).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    loginResponse.onResponse(response.body());
                } else {
                    loginResponse.onFailure(new Throwable("Login Failed: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });
    }

    public void fetchUserDetails(final DataCallback<User> callback) {
        userService.getUserDetails(null).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Failed to load user details: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(new Exception("Network error: " + t.getMessage()));
            }
        });
    }

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }

    public interface ILoginResponse {
        void onResponse(LoginResponse loginResponse);
        void onFailure(Throwable t);
    }
}
