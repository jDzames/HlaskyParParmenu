package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.app.Activity;
import android.os.Bundle;

//podla http://ics.upjs.sk/~novotnyr/android/pouzivatelske-nastavenia-appky.html

public class PreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_preferences);

        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new PreferencesFragment())
                .commit();
    }

}
