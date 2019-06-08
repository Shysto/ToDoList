package com.example.todolist.accueil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.api.response_class.Hash;
import com.example.todolist.api.TodoApiService;
import com.example.todolist.api.TodoApiServiceFactory;
import com.example.todolist.recycler_activities.ChoixListActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Définition de la classe MainActivity.
 * Cette classe représente l'activité principale de l'application
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /* Le champ texte dans lequel l'utilisateur va saisir son pseudo */
    EditText editTextPseudo;
    EditText password;
    String url;
    Button btnOk;
    private Call<Hash> call;
    SharedPreferences preferences;
    TodoApiService todoApiService;

    /** Fonction onCreate appelée lors de le création de l'activité
     * @param savedInstanceState données à récupérer si l'activité est réinitialisée après avoir planté
     * Lie l'activité à son layout et récupère les éléments avec lesquels on peut intéragir
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOk = findViewById(R.id.btnOk);
        editTextPseudo = findViewById(R.id.editTextPseudo);
        password = findViewById(R.id.password);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnOk.setOnClickListener(this);

    }



    /** Fonction onResume appelée lors de la reprise de l'activité principale après mise en pause pour cause d'appel à une autre activité
     * Permet de remplir par défaut le champ pseudo avec le denrier pseudo rentré
     * Le pseudo sera ainsi rafraichit à chaque fois
     */
    @Override
    protected void onResume() {
        super.onResume();
        /* Affichage du dernier pseudo saisi */
        editTextPseudo.setText(preferences.getString("pseudo",""));
        verfierUrl();
        btnOk.setEnabled(verifReseau());

    }

    /** Création de la ToolBar au démarrage de l'activité
     * @param menu le menu de la ToolBar qui contient les différents items
     * @return true pour que le menu soit affiché
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /** Traitement du clic sur un item de la ToolBar
     * @param item l'item du menu de la ToolBar sélectionné
     * @return true
     * Ici, un seul item est disponible, le clic ouvre l'activité Settings Activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(settingsIntent);
        return true;
    }


    /** Fonction par défaut de l'interface View.OnClickListener, appelée lors du clic sur la vue
     * @param v la vue cliquée
     * Ici, lors du clic sur le bouton OK, on ouvre l'activité ChoixListe Activity et on sauvegarde le pseudo dans les préférences
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk:
                sauverPseudo();
                sync();
                break;
            default:
        }
    }

    /** Permet d'ouvrir l'activité ChoixListe Activity
     * Fournit le pseudo rentré par l'utilisateur à cette nouvelle activité
     */
    private void ouvrirChoixListeActivity() {
        Intent choixListeActivity = new Intent(MainActivity.this, ChoixListActivity.class);
        choixListeActivity.putExtra("pseudo",editTextPseudo.getText().toString());
        startActivity(choixListeActivity);
    }

    /** Permet de sauvegarder le pseudo dans les préférences de l'application
     * Le pseudo est saisi par l'utilisateur
     */
    private void sauverPseudo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pseudo",editTextPseudo.getText().toString());
        editor.apply();
        editor.commit();
    }

    private void verfierUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(preferences.getString("url", "").equals("")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("url","http://tomnab.fr/todo-api/");
            editor.apply();
            editor.commit();
        }
        url = preferences.getString("url", "");
        TodoApiServiceFactory.changeApiBaseUrl(url);
    }

    public boolean verifReseau()
    {
        // On vérifie si le réseau est disponible,
        // si oui on change le statut du bouton de connexion
        ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();

        String sType = "Aucun réseau détecté";
        Boolean bStatut = false;
        if (netInfo != null)
        {

            NetworkInfo.State netState = netInfo.getState();

            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0)
            {
                bStatut = true;
                int netType= netInfo.getType();
                switch (netType)
                {
                    case ConnectivityManager.TYPE_MOBILE :
                        sType = "Réseau mobile détecté"; break;
                    case ConnectivityManager.TYPE_WIFI :
                        sType = "Réseau wifi détecté"; break;
                }

            }
        }

        Log.i("REZO", "verifReseau: " + sType );
        return bStatut;
    }

    private void sync() {

        todoApiService = TodoApiServiceFactory.createService(TodoApiService.class);

        call = todoApiService.connexion(editTextPseudo.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<Hash>() {
            @Override
            public void onResponse(Call<Hash> call, Response<Hash> response) {

                if(response.isSuccessful()){
                    // stocker hash et passer en bundle
                    //intent
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("hash",response.body().hash);
                    editor.apply();
                    editor.commit();


                    Log.i("Main", "onResponse: " + response.body().hash );

                    ouvrirChoixListeActivity();


                } else {
                    Log.d("TAG", "onResponse: "+response.code());
                    Toast.makeText(MainActivity.this,"Error code : "+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override public void onFailure(Call<Hash> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error code : " ,Toast.LENGTH_LONG).show();
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    @Override protected void onStop() {
        super.onStop();
        call.cancel();
    }
}
