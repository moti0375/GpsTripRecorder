package com.bartovapps.gpstriprec;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;

public class GpsRecPrefs extends PreferenceActivity {

    private static int prefs = R.xml.prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) < 21) {
            getActionBar().setTitle(getString(R.string.action_settings));
            getActionBar().setDisplayShowTitleEnabled(true);
            getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_launcher));
            getActionBar().setDisplayUseLogoEnabled(true);
            getActionBar().setLogo(getResources().getDrawable(R.drawable.ic_launcher));
        }
        super.onCreate(savedInstanceState);
        AddFragFromResource();
    }


    private void AddFragFromResource() {
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFrag()).commit();
    }


    public static class PreferencesFrag extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(GpsRecPrefs.prefs);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
                Preference preference = getPreferenceScreen().getPreference(i);
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
                        updatePreference(preferenceGroup.getPreference(j));
                    }
                } else {
                    updatePreference(preference);
                }
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            Toast.makeText(getActivity(), "Prefs changed..", Toast.LENGTH_LONG).show();
            updatePreference(findPreference(key));
        }

        private void updatePreference(Preference preference) {
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setSummary(listPreference.getEntry());
            }
        }
    }
}


	
	

