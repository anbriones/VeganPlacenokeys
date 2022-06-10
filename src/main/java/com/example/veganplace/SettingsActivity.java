package com.example.veganplace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private ListPreference mListPreference;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);


            ListPreference themePreference = getPreferenceManager().findPreference(getString(R.string.settings_theme_key));
            if (themePreference.getValue() == null) {
                themePreference.setValue(ThemeSetup.Mode.DEFAULT.name());
            }
            themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                ThemeSetup.applyTheme(ThemeSetup.Mode.valueOf((String) newValue));
                return true;
            });
        }

    }
}



