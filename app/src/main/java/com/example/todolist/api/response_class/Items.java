package com.example.todolist.api.response_class;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Items {

    @SerializedName("items")
    public List<UnItem> listeItems;

}
