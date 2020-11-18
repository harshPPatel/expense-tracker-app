package com.example.expensetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;

import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            SwitchPreferenceCompat switchPreference = findPreference("MODE");

            if (switchPreference != null) {
                switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        if ((Boolean) newValue) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        return true;
                    }
                });
            }

            ListPreference listPreference = findPreference("currency");

            if (listPreference != null) {
                listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        String newCurrency = (String) newValue;
                        Model model = Model.getInstance(getActivity().getApplication());
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");
                        if (!token.isEmpty()) {
                            model.updateCurrency(token, newCurrency, new AbstractListener() {
                                @Override
                                public void onCurrencyUpdated(String currency) {
                                    Toast.makeText(getContext(), "Updated currency to " + currency, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onRequestFailed(JSONObject jsonError) {
                                    Toast.makeText(getContext(), "ERROR while saving currency :(", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        return true;
                    }
                });
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}