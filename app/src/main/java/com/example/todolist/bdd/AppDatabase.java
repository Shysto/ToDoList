package com.example.todolist.bdd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todolist.api.response_class.UnItem;
import com.example.todolist.api.response_class.UneListe;
import com.example.todolist.api.response_class.User;

@Database(version = 1, entities = {User.class, UneListe.class, UnItem.class})
public abstract class AppDatabase extends RoomDatabase {

    abstract public UneListeDao listeDao();

    abstract public UserDao userDao();

    abstract public UnItemDao itemDao();
}
