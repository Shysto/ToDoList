package com.example.todolist.api;

import retrofit2.Call;
import retrofit2.http.POST;

public interface TodoApiService {

  @POST("authenticate?user=ulysse&password=fromage")
  Call<Hash> connexion();
}
