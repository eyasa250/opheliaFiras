package com.example.returnto0.user;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import com.example.returnto0.RetrofitClient;
import com.example.returnto0.ui.profile.User;

public class MainViewModel extends AndroidViewModel {
    private static final String SHARED_PREFS_FILE = "app_prefs";
    private static final String TOKEN_KEY = "token";

    private MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    private MainRepository mMainRepository;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mLoginResultMutableData.postValue("Not logged in");
        initRepository(); // Initialize repository based on the stored token
    }

    private void initRepository() {
        String token = getToken();
        mMainRepository = new MainRepository();
    }

    public void login(String email, String password) {
        // Use a temporary repository instance without token for login
        MainRepository loginRepository = new MainRepository();
        loginRepository.loginRemote(new LoginBody(email, password), new MainRepository.ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                if (loginResponse != null && loginResponse.getToken() != null) {
                    saveToken(loginResponse.getToken());
                    mLoginResultMutableData.postValue("Login Successful");
                    initRepository(); // Re-initialize repository with new token
                } else {
                    mLoginResultMutableData.postValue("Login Failed: Token not received");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginResultMutableData.postValue("Login failure: " + t.getLocalizedMessage());
            }
        });
    }

    public void fetchUserDetails() {
        if (mMainRepository != null) {
            mMainRepository.fetchUserDetails(new MainRepository.DataCallback<User>() {
                @Override
                public void onSuccess(User data) {
                    userLiveData.postValue(data);
                }

                @Override
                public void onError(Exception e) {
                    errorLiveData.postValue(e.getMessage());
                }
            });
        }
    }

    public void saveToken(String token) {
        SharedPreferences prefs = getApplication().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences prefs = getApplication().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }

    public LiveData<String> getLoginResult() {
        return mLoginResultMutableData;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}