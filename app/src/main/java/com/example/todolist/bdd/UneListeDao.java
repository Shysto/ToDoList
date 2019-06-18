package com.example.todolist.bdd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todolist.api.response_class.UneListe;

import java.util.List;

@Dao
public interface UneListeDao {

    @Query("SELECT * FROM lists WHERE idUser LIKE :idUser")
    List<UneListe> getAll(int idUser);

    @Insert
    void insertAll(UneListe... todolists);
}
