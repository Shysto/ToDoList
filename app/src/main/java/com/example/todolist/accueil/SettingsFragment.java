package com.example.todolist.accueil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.todolist.R;

/** Définition de la classe Settings Fragment.
 * Cette classe permet d'utiliser des fragments pour nos préférences (à la place d'une PreferenceActivity)
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    EditTextPreference url;

    /** Fonction qui permet de fournir les préférences pour le fragment lors de sa création
     * @param savedInstanceState contient l'état du fragment s'il est re-créé à partir d'un état antérieur
     * @param rootKey
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        setPreferencesFromResource(R.xml.preferences,rootKey);
        Preference pseudoPreference = findPreference("Pseudo");
        pseudoPreference.setTitle(preferences.getString("pseudo",""));
        url = (EditTextPreference) findPreference("url");

        url.setSummary(preferences.getString("url","http://tomnab.fr/todo-api"));
        preferences.registerOnSharedPreferenceChangeListener(this);


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("url")) {
            Log.i("url", "Preference value was updated to: " + sharedPreferences.getString(key, ""));

            url.setSummary(sharedPreferences.getString(key,""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
