package com.example.todolist.bdd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.api.response_class.UnItem;
import com.example.todolist.api.response_class.UneListe;

import java.util.List;

@Dao
public interface UnItemDao {
    @Query("SELECT * FROM items WHERE idListe LIKE :idListe")
    List<UnItem> getAll(int idListe);

    @Update
    void updateUsers(UnItem... item);

    @Insert
    void insertAll(UnItem... items);
}
