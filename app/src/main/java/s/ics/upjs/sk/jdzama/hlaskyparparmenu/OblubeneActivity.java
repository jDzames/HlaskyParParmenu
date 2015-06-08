package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class OblubeneActivity extends Activity {

    private FragmentManager fragmentManager;
    private Fragment oblubeneFragment;
    private static final String OBLUBENE_FRAGMENT = "oblubeneFr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oblubene);

        fragmentManager = getFragmentManager();
        oblubeneFragment = new OblubeneFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, oblubeneFragment, OBLUBENE_FRAGMENT)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oblubene, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
