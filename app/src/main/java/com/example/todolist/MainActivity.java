package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextPseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOk = findViewById(R.id.btnOk);
        editTextPseudo = findViewById(R.id.editTextPseudo);

        btnOk.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(settingsIntent);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk:
                sauverPseudo();
                ouvrirChoixListeActivity();
                break;
            default:
        }
    }

    private void ouvrirChoixListeActivity() {
        Intent choixListeActivity = new Intent(MainActivity.this,ChoixListeActivity.class);
        choixListeActivity.putExtra("pseudo",editTextPseudo.getText().toString());
        startActivity(choixListeActivity);
    }

    private void sauverPseudo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pseudo",editTextPseudo.getText().toString());
        editor.apply();
        editor.commit();
    }
}
