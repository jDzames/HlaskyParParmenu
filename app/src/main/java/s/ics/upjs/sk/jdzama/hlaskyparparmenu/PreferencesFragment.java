package s.ics.upjs.sk.jdzama.hlaskyparparmenu;


import android.content.AsyncQueryHandler;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.OblubeneContentProvider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.util.Defaults;

//podla http://ics.upjs.sk/~novotnyr/android/pouzivatelske-nastavenia-appky.html

public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int DELETE_OBLUBENE_TOKEN = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        setComponents();
    }

    private void setComponents() {
        Preference deletePreference = findPreference("deleteOblubeneDatabase");
        deletePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                deleteAllHistory();
                return true;
            }
        });
    }

    private void deleteAllHistory() {
        //TODO leak?
        AsyncQueryHandler deleteHandler = new AsyncQueryHandler(getActivity().getContentResolver()) {
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                Toast.makeText(getActivity(), "Zmazané.", Toast.LENGTH_SHORT)
                        .show();
            }
        };
        Uri trainingsHistory = OblubeneContentProvider.CONTENT_URI;
        deleteHandler.startDelete(DELETE_OBLUBENE_TOKEN, Defaults.NO_COOKIE, trainingsHistory,
                Defaults.NO_SELECTION, Defaults.NO_SELECTION_ARGS);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
