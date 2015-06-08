package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

//db prepojenie podla http://ics.upjs.sk/~novotnyr/blog/2050/systemove-programovanie-2015-poziadavky-na-hodnotenie

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.OblubeneContentProvider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.Provider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.util.Defaults;


/**
 * A simple {@link Fragment} subclass.
 */
public class OblubeneFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private static final int DELETE_OBLUBENE_TOKEN = 0;
    private View rootView;
    private SimpleCursorAdapter adapter;
    private static final int OBLUBENE_LOADER_ID = 0;

    private MediaPlayer player;

    public OblubeneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_oblubene, container, false);

        getLoaderManager().initLoader(OBLUBENE_LOADER_ID, Bundle.EMPTY, this);

        ListView listOblubene = (ListView) rootView.findViewById(R.id.listOblubene);
        listOblubene.setAdapter(initializeAdapter());
        listOblubene.setOnItemClickListener(this);
        listOblubene.setOnItemLongClickListener(this);

        player = new MediaPlayer();

        return rootView;
    }

    @Override
    public void onDestroy() {
        if (player!=null){
            player.stop();
            player.release();
            player = null;
        }
        super.onDestroy();
    }

    private ListAdapter initializeAdapter() {
        String[] from = {Provider.Oblubene.NAZOV, Provider.Oblubene.AUTOR};
        int[] to = {R.id.oblubeneListViewTitle, R.id.oblubeneListViewAthor};
        this.adapter = new SimpleCursorAdapter(rootView.getContext(), R.layout.oblubene, Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        return this.adapter;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = new CursorLoader(rootView.getContext());
        loader.setUri(OblubeneContentProvider.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        this.adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.adapter.swapCursor(Defaults.NO_CURSOR);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor selectedOblubenaCursor = (Cursor) adapterView.getItemAtPosition(i);
        int rawIdColumnIdx = selectedOblubenaCursor.getColumnIndex(Provider.Oblubene.RAW_ID);
        int rawId = selectedOblubenaCursor.getInt(rawIdColumnIdx);

        player.reset();
        player = MediaPlayer.create(rootView.getContext(), rawId);
        player.start();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long id) {
        Cursor selectedOblubenaCursor = (Cursor) adapterView.getItemAtPosition(i);

        int rawTitleColumnIdx = selectedOblubenaCursor.getColumnIndex(Provider.Oblubene.NAZOV);
        String title = selectedOblubenaCursor.getString(rawTitleColumnIdx);
        int rawAutorColumnIdx = selectedOblubenaCursor.getColumnIndex(Provider.Oblubene.AUTOR);
        String author = selectedOblubenaCursor.getString(rawAutorColumnIdx);

        TextView viewTitle = createTitleView();

        new AlertDialog.Builder(rootView.getContext())
                .setMessage("Odstrániť hlášku: "+author+" - "+title+" spomedzi obľúbených?")
                .setCustomTitle(viewTitle)
                .setPositiveButton("Zmazať", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOblubenu(id);
                    }
                })
                .setNegativeButton("Zrušiť", Defaults.DISMISS_ACTION)
                .show();

        return true;
    }

    private TextView createTitleView(){
        TextView viewTitle = new TextView(rootView.getContext());
        viewTitle.setTextColor(getResources().getColor(R.color.accent_material_light));
        viewTitle.setText("Zmazanie hlášky");
        viewTitle.setTextSize(20);
        viewTitle.setPadding(5,2,3,2);
        return viewTitle;
    }

    private static AsyncQueryHandler deleteHandler;

    private void deleteOblubenu(long id){
        AsyncQueryHandler deleteHandler = new AsyncQueryHandler(rootView.getContext().getContentResolver()) {
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                Toast.makeText(rootView.getContext(), "Hláška bola odstránená z obľúbených", Toast.LENGTH_SHORT)
                        .show();
            }
        };
        Uri selectedOblubenaUri = ContentUris.withAppendedId(OblubeneContentProvider.CONTENT_URI, id);
        deleteHandler.startDelete(DELETE_OBLUBENE_TOKEN, Defaults.NO_COOKIE, selectedOblubenaUri,
                Defaults.NO_SELECTION, Defaults.NO_SELECTION_ARGS);
    }


}
