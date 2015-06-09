package s.ics.upjs.sk.jdzama.hlaskyparparmenu;


import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.OblubeneContentProvider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.Provider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.util.Defaults;


public class AddOblubeneFragment extends Fragment implements AdapterView.OnItemClickListener {


    private static final int INSERT_NOTE_TOKEN = 0;
    private View rootView;
    private ListView addHlaskyListView;
    private ArrayList<Song> myList;

    public AddOblubeneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_oblubene, container, false);

        prepareListView();

        return rootView;
    }

    private void prepareListView() {
        addHlaskyListView = (ListView) rootView.findViewById(R.id.addHlaskyListView);

        if (HlaskyList.INSTANCE.hlaskyVsetky==null){
            HlaskyList.INSTANCE.makeList();
        }
        myList = HlaskyList.INSTANCE.hlaskyVsetky;

        //http://stackoverflow.com/questions/11256563/how-to-set-both-lines-of-a-listview-using-simple-list-item-2
        ArrayAdapter<Song> hlaskyAdapter = new ArrayAdapter<Song>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, myList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(myList.get(position).getTitle());
                //text2.setTextColor(getResources().getColor(Color.DKGRAY));
                text2.setText(myList.get(position).getArtist());
                /*if (myList.get(position).isJeOblubena()){
                    view.setBackgroundColor(getResources().getColor(R.color.accent_material_light));
                }*/
                return view;
            }
        };

        addHlaskyListView.setAdapter(hlaskyAdapter);
        addHlaskyListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //view.setBackgroundColor(getResources().getColor(R.color.accent_material_light));
        Song thisSong = myList.get(i);
        if (!thisSong.isJeOblubena()) {
            thisSong.setJeOblubena(true);
            insertIntoContentProvider(thisSong);
        }
    }

    //podla ics.upjs.sk/~novotnyr/android/5-stretnutie-zlte-poznamkove-papieriky.html
    private void insertIntoContentProvider(Song song) {
        Uri uri = OblubeneContentProvider.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Provider.Oblubene.RAW_ID, song.getID());
        values.put(Provider.Oblubene.NAZOV, song.getTitle());
        values.put(Provider.Oblubene.AUTOR, song.getArtist());
        final String message = getResources().getString(R.string.hlaska_pridana);

        AsyncQueryHandler insertHandler = new AsyncQueryHandler(rootView.getContext().getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(rootView.getContext(), message, Toast.LENGTH_SHORT)
                        .show();
            }
        };

        insertHandler.startInsert(INSERT_NOTE_TOKEN, Defaults.NO_COOKIE, uri, values);
    }
}
