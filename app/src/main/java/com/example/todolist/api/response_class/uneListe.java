package com.example.todolist.api.response_class;

import com.google.gson.annotations.SerializedName;

public class uneListe {

    @SerializedName("id")
    public int id;

    @SerializedName("label")
    public String titreListeToDO;
}
