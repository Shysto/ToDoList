package com.example.todolist.recycler_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.modele.ListeToDo;
import com.example.todolist.modele.ProfilListeToDo;
import com.example.todolist.recycler_activities.adapter.ItemAdapterList;

public class ChoixListeActivity extends Library implements View.OnClickListener,
        ItemAdapterList.onClickListListener {

    private String pseudo;
    private EditText ajouterListe;
    private ProfilListeToDo profil;
    private ItemAdapterList itemAdapterList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pseudo = preferences.getString("pseudo","");
        profil = importProfil(pseudo);

        setContentView(R.layout.activity_choixliste);
        recyclerView = findViewById(R.id.recyclerView);
        itemAdapterList = new ItemAdapterList(profil.getMesListesToDo(),this);
        recyclerView.setAdapter(itemAdapterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnOk = findViewById(R.id.btnOk2);
        btnOk.setOnClickListener(this);
        ajouterListe = findViewById(R.id.ajouterListe);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk2:
                ListeToDo listeToDo = new ListeToDo();
                listeToDo.setTitreListeToDo(ajouterListe.getText().toString());
                Log.i("ChoixListe", "onClick: " + listeToDo.toString());
                profil.ajouteListe(listeToDo);
                itemAdapterList.notifyItemInserted(profil.getMesListesToDo().size()-1);
                Log.i("ChoixListe", "onClick: " + itemAdapterList.getItemCount());
                break;
                default:
                    return;

        }
    }

    @Override
    public void clickList(int position) {
        Intent showListIntent = new Intent(this,ShowListActivity.class);
        showListIntent.putExtra("idListe",position);
        startActivity(showListIntent);
    }
}
