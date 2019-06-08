package com.example.todolist.api;

import com.example.todolist.api.response_class.Hash;
import com.example.todolist.api.response_class.Lists;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TodoApiService {

  @POST("authenticate")
  Call<Hash> connexion(@Query("user") String user, @Query("password") String password);

  @GET("lists")
  Call<Lists> recupereListes(@Header("hash") String hash);

}
