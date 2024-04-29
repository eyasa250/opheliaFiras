package com.example.returnto0.user;

import com.google.gson.annotations.SerializedName;

public class SignUpBody {
    @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
