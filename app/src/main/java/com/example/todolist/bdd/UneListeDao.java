package com.example.todolist.bdd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todolist.api.response_class.UneListe;

import java.util.List;

@Dao
public interface UneListeDao {

    @Query("SELECT * FROM lists WHERE hash = 'ac74471412026f677735d412e4a07a20' ")
    List<UneListe> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UneListe> todolists);
}
