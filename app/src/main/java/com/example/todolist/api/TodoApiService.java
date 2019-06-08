package com.example.todolist.api;

import com.example.todolist.modele.ListeToDo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TodoApiService {

  @POST("authenticate")
  Call<Hash> connexion(@Query("user") String user, @Query("password") String password);

  @GET("lists")
  Call<ListeToDo> recupereListes(@Header("hash") String hash);

}
