package com.example.todolist.recycler_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.api.TodoApiService;
import com.example.todolist.api.TodoApiServiceFactory;
import com.example.todolist.api.response_class.Lists;
import com.example.todolist.api.response_class.uneListe;
import com.example.todolist.modele.ListeToDo;
import com.example.todolist.modele.ProfilListeToDo;
import com.example.todolist.recycler_activities.adapter.ItemAdapterList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Définition de la classe ChoixListActivity.
 * Cette classe représente l'activité ChoixListe Activity de l'application
 */
public class ChoixListActivity extends Library implements View.OnClickListener,
        ItemAdapterList.onClickListListener {

    /* Le pseudo rentré par l'utilisateur dans l'activité principale */
    private String pseudo;
    /* Le titre de la nouvelle ToDoList à ajouter, saisi par l'utilisateur dans l'activité courante */
    private EditText ajouterListe;
    /* Le profil associé au pseudo */
    private ProfilListeToDo profil;
    /* L'adapteur associé à la Recycler View de l'activité courante */
    private ItemAdapterList itemAdapterList;
    /* La Recycle View de l'activité courante */
    private RecyclerView recyclerView;
    private String hash;
    TodoApiService todoApiService;
    private Call<Lists> call;
    private SharedPreferences preferences;
    private List<ListeToDo> data;


    /** Fonction onCreate appelée lors de le création de l'activité
     * @param savedInstanceState données à récupérer si l'activité est réinitialisée après avoir planté
     * Lie l'activité à son layout et récupère les éléments avec lesquels on peut intéragir
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choixliste);

        /* Récupération du pseudo depuis les préférences de l'application */
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pseudo = preferences.getString("pseudo","");
        hash = preferences.getString("hash","");


        /* Traitement de l'ajout d'une ToDoList au profil */
        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        ajouterListe = findViewById(R.id.ajouterListe);
    }

    /** Fonction onPause appelée lors d'un changement d'activité au détriment de celle-ci
     * Permet de sauvegarder le profil courant dans les préférences
     */
    @Override
    protected void onPause() {
        super.onPause();

        //Envoyer la liste de listes a l'API
    }

    /** Fonction onResume appelée après la création de l'activité et à chaque retour sur l'activité courante
     * Permet de recharger le profil courant à partir du pseudo et de générer la RecyclerView associée
     *      * à la liste des ToDoLists
     */
    @Override
    protected void onResume() {
        super.onResume();

        /* Import du profil associé */
        //profil = importProfil(pseudo);

        Log.i("TAG", "onResume: sync");

        sync();

        Log.i("TAG", "onResume: post sync");

        /* Mise en place de la Recycler View sur la liste des ToDoLists associée au profil*/


    }

    /** Fonction par défaut de l'interface View.OnClickListener, appelée lors du clic sur la vue
     * @param v la vue cliquée
     * Ici, lors du clic sur le bouton OK, on crée la ToDoList avec le titre fourni par l'utilisateur,
     *          et on l'ajoute à la liste des ToDoLists du profil
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk:
                ListeToDo listeToDo = new ListeToDo();
                listeToDo.setTitreListeToDo(ajouterListe.getText().toString());
                Log.i("ChoixListe", "onClick: " + listeToDo.toString());
                profil.ajouteListe(listeToDo);
                itemAdapterList.notifyItemInserted(profil.getMesListesToDo().size()-1);
                Log.i("ChoixListe", "onClick: " + itemAdapterList.getItemCount());
                break;
            default:
        }
    }

    /** Permet d'ouvrir l'activité ShowList Activity lors du clic sur une des ToDoLists
     * @param position l'indice où se trouve la ToDoList dans la liste des ToDoLists
     */
    @Override
    public void clickList(int position) {
        Intent showListIntent = new Intent(this, ShowListActivity.class);
        showListIntent.putExtra("idListe", data.get(position).getId());
        startActivity(showListIntent);
    }


    private void sync() {

        todoApiService = TodoApiServiceFactory.createService(TodoApiService.class);

        Log.i("TAG", "oncall: 1");
        call = todoApiService.recupereListes(hash);

        Log.i("TAG", "oncall: 2");


        call.enqueue(new Callback<Lists>() {
            @Override
            public void onResponse(Call<Lists> call, Response<Lists> response) {
                Log.i("TAG", "onResponse: 3");
                if(response.isSuccessful()){
                    //stocke les listes
                    List<uneListe> lists = response.body().listeDeListes;
                    data = new ArrayList<ListeToDo>();
                    for (uneListe x : lists) {
                        data.add(new ListeToDo(x.titreListeToDO,x.id));
                        Log.i("TAG", "onResponse: " + x.id + " " + x.titreListeToDO);
                    }
                    recyclerView = findViewById(R.id.recyclerView);
                    itemAdapterList = new ItemAdapterList(data,ChoixListActivity.this);
                    recyclerView.setAdapter(itemAdapterList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ChoixListActivity.this));

                }else {
                    Log.d("TAG", "onResponse: "+response.code());
                }
            }
            @Override public void onFailure(Call<Lists> call, Throwable t) {
                Toast.makeText(ChoixListActivity.this,"Error code : " ,Toast.LENGTH_LONG).show();
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }
}
