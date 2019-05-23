package com.example.todolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import modele.ProfilListeToDo;

public class ChoixListeActivity extends Library{

    private String pseudo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pseudo = preferences.getString("pseudo","");
        ProfilListeToDo profil = importProfil(pseudo);
    }

}
