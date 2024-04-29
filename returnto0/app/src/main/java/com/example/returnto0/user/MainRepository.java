package com.example.returnto0.user;

import android.util.Log;

import com.example.returnto0.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    String[] drinksListRemote = {"Spiking coffee", "Sweet Bananas", "Tomato Tang",
            "Apple Berry Smoothie", "Coding Reel Coffee"};
    UserService UserService = RetrofitClient.getClient().create(UserService.class);

    public MainRepository() {
    }

    public void loginRemote(LoginBody loginBody, ILoginResponse loginResponse){
        Log.d("MainRepository", "Login Role: " + loginBody.getRole());

        Call<LoginResponse> initiateLogin = UserService.login(loginBody);


        initiateLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    loginResponse.onResponse(response.body());
                } else {
                    loginResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });

    }

    public void signUpRemote(SignUpBody signUpBody, ISignUpResponse signUpResponse) {
        Call<SignUpResponse> signUpCall = UserService.signup(signUpBody);

        signUpCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    signUpResponse.onResponse(response.body());
                } else {
                    signUpResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                signUpResponse.onFailure(t);
            }
        });
    }

    public interface ISignUpResponse {
        void onResponse(SignUpResponse signUpResponse);
        void onFailure(Throwable t);
    }
    public interface ILoginResponse{
        void onResponse(LoginResponse loginResponse);
        void onFailure(Throwable t);
    }


}
