package com.example.todolist.api;

import com.example.todolist.api.response_class.Hash;
import com.example.todolist.api.response_class.ItemResponse;
import com.example.todolist.api.response_class.Items;
import com.example.todolist.api.response_class.Lists;
import com.example.todolist.api.response_class.UnItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TodoApiService {

  @POST("authenticate")
  Call<Hash> connexion(@Query("user") String user, @Query("password") String password);

  @GET("lists")
  Call<Lists> recupereListes(@Header("hash") String hash);

  @GET("lists/{id}/items")
  Call<Items> recupereItems(@Header("hash") String hash, @Path("id") int idListe);

  @PUT("lists/{idListe}/items/{idItem}")
  Call<UnItem> cocherItem(@Header("hash") String hash, @Path("idListe") int idListe,
                          @Path("idItem") int idItem, @Query("check") int etat);

  @POST("lists/{idListe}/items")
  Call<ItemResponse> ajoutItem(@Header("hash") String hash, @Path("idListe") int idListe,
                               @Query("label") String label);
}
