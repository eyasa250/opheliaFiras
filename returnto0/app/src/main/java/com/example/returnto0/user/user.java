package com.example.returnto0.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("_id")
    private String id;

    public User(String name) {
        this.name = name;
    }

    @SerializedName("nom")
    private String name;


    @SerializedName("admin")
    private String admin;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
