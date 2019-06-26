package com.example.todolist.bdd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.api.response_class.UnItem;

import java.util.List;

@Dao
public interface UnItemDao {
    @Query("SELECT * FROM items WHERE idListe LIKE :idListe")
    List<UnItem> getAll(int idListe);

    @Update
    void updateItem(UnItem item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UnItem> items);
}
