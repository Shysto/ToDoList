package com.example.todolist.api;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TodoApiService {

  @POST("authenticate")
  Call<Hash> connexion(@Query("user") String user, @Query("password") String password);

}
