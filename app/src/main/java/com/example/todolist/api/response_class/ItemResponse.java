package com.example.todolist.api.response_class;

import com.google.gson.annotations.SerializedName;

public class ItemResponse {

    @SerializedName("item")
    public UnItem item;
}
