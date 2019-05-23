package com.example.todolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import android.preference.PreferenceManager;

import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        setPreferencesFromResource(R.xml.preferences,rootKey);
        Preference pseudoPreference = findPreference("Pseudo");
        pseudoPreference.setTitle(preferences.getString("pseudo",""));
    }
}
