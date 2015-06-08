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
        TextView view = new TextView(getApplicationContext());
        view.setTextColor(getResources().getColor(R.color.accent_material_light));
        view.setText("Zobraziť obľúbené");
        view.setTextSize(20);
        view.setPadding(5, 2, 3, 2);
        view.setTextIsSelectable(false);
        new AlertDialog.Builder(this)
                //.setTitle("Pridávanie a odstraňovanie")
                .setCustomTitle(view)
                .setMessage("Naozaj zobraziť obľúbené? (V okne, v ktorom sa práve nachádzate môžete klikaním " +
                        "pridať hlášky k obľúbeným, v druhom okne môžte "+
                        "odstrániť hlášku z obľúbených dlhším podržaním.)")
                .setCancelable(false)
                .setPositiveButton("Áno", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showOblubeneFragment();
                    }
                })
                .setNegativeButton("Nie", Defaults.DISMISS_ACTION)
                .show();
    }

    private void askForAdd() {
        TextView view = new TextView(getApplicationContext());
        view.setTextColor(getResources().getColor(R.color.accent_material_light));
        view.setText("Pridávanie k obľúbeným");
        view.setTextSize(20);
        view.setPadding(5,2,3,2);
        view.setTextIsSelectable(false);
        new AlertDialog.Builder(this)
                //.setTitle("Pridávanie a odstraňovanie")
                .setCustomTitle(view)
                .setMessage("Prejsť na pridávanie k obľúbeným? (V okne, v ktorom sa práve nachádzate môžete " +
                        "odstrániť hlášku z obľúbených dlhším podržaním, v druhom okne môžte klikaním " +
                        "pridať hlášky k obľúbeným.)")
                .setCancelable(false)
                .setPositiveButton("Áno", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAddOblubeneFragment();
                    }
                })
                .setNegativeButton("Nie", Defaults.DISMISS_ACTION)
                .show();
    }


}
