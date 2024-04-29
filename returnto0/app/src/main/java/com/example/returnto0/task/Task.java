package com.example.returnto0.task;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    private String id;

    public String getName() {
        return name;
    }


    // Constructor, getters, and setters
}
