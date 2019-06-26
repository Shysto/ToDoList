package com.example.todolist.bdd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todolist.api.response_class.UneListe;

import java.util.List;

@Dao
public interface UneListeDao {

    @Query("SELECT * FROM lists WHERE hash = :hash ")
    List<UneListe> getAll(String hash);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UneListe> todolists);
}
