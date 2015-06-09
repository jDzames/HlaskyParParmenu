package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.util.Defaults;

//podla http://ics.upjs.sk/~novotnyr/android/7-stretnutie-markdown-editor.html

public class OblubeneActivity extends Activity {

    private FragmentManager fragmentManager;
    private static final String OBLUBENE_FRAGMENT = "oblubeneFragment";
    private static final String ADD_OBLUBENE_FRAGMENT = "addOblubeneFragment";
    public static final String DEFAULT_BACKSTACK_NAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oblubene);

        fragmentManager = getFragmentManager();

        if(savedInstanceState == null) {
            showOblubeneFragment();
        }

    }

    private void showOblubeneFragment(){
        OblubeneFragment oblubeneFragment = (OblubeneFragment) fragmentManager.findFragmentByTag(OBLUBENE_FRAGMENT);

        if (oblubeneFragment == null){
            oblubeneFragment = new OblubeneFragment();
        }

        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, oblubeneFragment, OBLUBENE_FRAGMENT)
                .commit();
    }

    private void showAddOblubeneFragment(){
        AddOblubeneFragment addOblubeneFragment = new AddOblubeneFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, addOblubeneFragment, ADD_OBLUBENE_FRAGMENT)
                .addToBackStack(DEFAULT_BACKSTACK_NAME)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oblubene, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem addOblubene = menu.findItem(R.id.addOblubene);
        MenuItem showOblubene = menu.findItem(R.id.showOblubene);

        if (isShowingOblubene()){
            showOblubene.setVisible(false);
            addOblubene.setVisible(true);
        }else{
            addOblubene.setVisible(false);
            showOblubene.setVisible(true);
        }
        return true;
    }

    private boolean isShowingOblubene(){
        return findViewById(R.id.listOblubene)!=null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addOblubene){
            askForAdd();
            invalidateOptionsMenu();
            return true;
        }
        if (id == R.id.showOblubene){
            askForShowOblubene();
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void askForShowOblubene() {
        if (isShowingOblubene()){
            return;
        }
        TextView view = new TextView(getApplicationContext());
        view.setTextColor(getResources().getColor(R.color.accent_material_light));
        view.setText(getString(R.string.ukazat_oblubene)+"?");
        view.setTextSize(20);
        view.setPadding(5, 2, 3, 2);
        view.setTextIsSelectable(false);
        String ano = getResources().getString(R.string.ano);
        String zrusit = getResources().getString(R.string.zrusit);
        new AlertDialog.Builder(this)
                //.setTitle("Pridávanie a odstraňovanie")
                .setCustomTitle(view)
                .setMessage(getString(R.string.zobrazit_oblubene_hint))
                .setCancelable(false)
                .setPositiveButton(ano, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showOblubeneFragment();
                    }
                })
                .setNegativeButton(zrusit, Defaults.DISMISS_ACTION)
                .show();
    }

    private void askForAdd() {
        if (!isShowingOblubene()){
            return;
        }
        TextView view = new TextView(getApplicationContext());
        view.setTextColor(getResources().getColor(R.color.accent_material_light));
        view.setText(getString(R.string.zobrazit_pridavanie_title));
        view.setTextSize(20);
        view.setPadding(5,2,3,2);
        view.setTextIsSelectable(false);
        String ano = getResources().getString(R.string.ano);
        String zrusit = getResources().getString(R.string.zrusit);
        String message = getResources().getString(R.string.prejst_na_pridavanie_hint);
        new AlertDialog.Builder(this)
                //.setTitle("Pridávanie a odstraňovanie")
                .setCustomTitle(view)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(ano, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAddOblubeneFragment();
                    }
                })
                .setNegativeButton(zrusit, Defaults.DISMISS_ACTION)
                .show();
    }
}
