package com.example.returnto0.user;

import com.google.gson.annotations.SerializedName;

public class admin {
    @SerializedName("_id")
    private String id;

    public admin(String name) {
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
