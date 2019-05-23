package com.example.todolist;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import modele.ProfilListeToDo;

public class Library extends AppCompatActivity {

    public ProfilListeToDo importProfil(String pseudo)
    {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String filename = pseudo +"_json";
        FileInputStream inputStream;
        String sJsonLu="";

        ProfilListeToDo profil = new ProfilListeToDo();


        /* Import du fichier JSON de sauvegarde dans l'objet */

        try {
            inputStream = openFileInput(filename);
            int content;
            while ((content = inputStream.read()) != -1) {
                // convert to char and display it
                sJsonLu = sJsonLu+(char)content;
            }
            inputStream.close();

            profil = gson.fromJson(sJsonLu,ProfilListeToDo.class);
        }
        catch (Exception e) {

            /* Creation d'un profil par defaut */
            profil.setLogin(pseudo);
            Log.i("TODO_ISA","Création du profil par défaut " + profil.getLogin());

            String fileContents = gson.toJson(profil);
            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
                Log.i("TODO_ISA","Création du fichier monprofil_json");
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.i("TODO_ISA","Impossible de créer le fichier de sauvegarde du profil par défaut");
            }


        }

        return profil;
    }

    public void sauveProfilToJsonFile(ProfilListeToDo profil)
    {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String filename = profil.getLogin() + "_json";
        String fileContents = gson.toJson(profil);
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
            Log.i("TODO_ISA","Sauvegarde du fichier monprofil_json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
