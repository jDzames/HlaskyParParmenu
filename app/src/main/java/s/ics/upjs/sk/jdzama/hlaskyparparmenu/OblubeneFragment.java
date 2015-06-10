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
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

        this.setRetainInstance(true);

        getLoaderManager().initLoader(OBLUBENE_LOADER_ID, Bundle.EMPTY, this);

        ListView listOblubene = (ListView) rootView.findViewById(R.id.listOblubene);
        listOblubene.setAdapter(initializeAdapter());
        listOblubene.setOnItemClickListener(this);
        listOblubene.setOnItemLongClickListener(this);

        if (player==null) {
            player = new MediaPlayer();
        }
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
        this.adapter.setViewBinder(new MyViewBinder());
        return this.adapter;
    }

    //podla http://developer.android.com/reference/android/widget/SimpleCursorAdapter.ViewBinder.html#setViewValue%28android.view.View,%20android.database.Cursor,%20int%29
   //a podla http://stackoverflow.com/questions/7628824/using-viewbinder-for-a-custom-listview-item-with-textview

    private class MyViewBinder implements  SimpleCursorAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            int viewId = view.getId();
            switch(viewId) {
                case R.id.oblubeneListViewTitle:
                    return false;
                case R.id.oblubeneListViewAthor:
                    TextView autorView = (TextView) view;
                    String cursorColumnData = cursor.getString(columnIndex);
                    switch(cursorColumnData) {
                        case "Bobromil":
                            autorView.setBackgroundColor(Color.CYAN);
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Hobitofil":
                            autorView.setBackgroundColor(Color.rgb(70,170,200));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Fritol":
                            autorView.setBackgroundColor(Color.rgb(230,250,50));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Šmajdalf":
                            autorView.setBackgroundColor(Color.rgb(243,91,36));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Bimbo":
                            autorView.setBackgroundColor(Color.rgb(91,243,126));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Chobot":
                            autorView.setBackgroundColor(Color.rgb(252,84,84));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Elrond":
                            autorView.setBackgroundColor(Color.rgb(222,125,51));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Bbbbb":
                            autorView.setBackgroundColor(Color.rgb(172,87,247));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Pajzl":
                            autorView.setBackgroundColor(Color.rgb(87,247,183));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Legoland":
                            autorView.setBackgroundColor(Color.rgb(190,190,190));
                            autorView.setText(cursorColumnData);
                            return true;
                        case "Barman":
                            autorView.setBackgroundColor(Color.rgb(228,10,112));
                            autorView.setText(cursorColumnData);
                            return true;
                    }
                    break;
            }
            return false;
        }
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

        String zmazat = getResources().getString(R.string.zmazat);
        String zrusit = getResources().getString(R.string.zrusit);
        String message = getResources().getString(R.string.delete_question);
        new AlertDialog.Builder(rootView.getContext())
                .setMessage(message + author+" - "+title+"?")
                .setCustomTitle(viewTitle)
                .setPositiveButton(zmazat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOblubenu(id);
                    }
                })
                .setNegativeButton(zrusit, Defaults.DISMISS_ACTION)
                .show();

        return true;
    }

    private TextView createTitleView(){
        String title = getResources().getString(R.string.title_zmazanie);
        TextView viewTitle = new TextView(rootView.getContext());
        viewTitle.setTextColor(getResources().getColor(R.color.accent_material_light));
        viewTitle.setText(title);
        viewTitle.setTextSize(20);
        viewTitle.setPadding(5,2,3,2);
        return viewTitle;
    }


    private void deleteOblubenu(long id){
        final String messsage = getResources().getString(R.string.hlaska_odstranena);
        AsyncQueryHandler deleteHandler = new AsyncQueryHandler(rootView.getContext().getContentResolver()) {
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                Toast.makeText(rootView.getContext(), messsage, Toast.LENGTH_SHORT)
                        .show();
            }
        };
        Uri selectedOblubenaUri = ContentUris.withAppendedId(OblubeneContentProvider.CONTENT_URI, id);
        deleteHandler.startDelete(DELETE_OBLUBENE_TOKEN, Defaults.NO_COOKIE, selectedOblubenaUri,
                Defaults.NO_SELECTION, Defaults.NO_SELECTION_ARGS);
    }


}
