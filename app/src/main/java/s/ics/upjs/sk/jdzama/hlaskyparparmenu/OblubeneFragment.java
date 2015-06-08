package s.ics.upjs.sk.jdzama.hlaskyparparmenu;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.OblubeneContentProvider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.Provider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.util.Defaults;


/**
 * A simple {@link Fragment} subclass.
 */
public class OblubeneFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private View rootView;
    private SimpleCursorAdapter adapter;
    private static final int OBLUBENE_LOADER_ID = 0;

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
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
}
