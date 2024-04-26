package com.example.returnto0.room;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Room  implements Serializable {
    @SerializedName("_id")
    private String id;

    public Room(String name) {
        this.name = name;
    }

    @SerializedName("nom")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
