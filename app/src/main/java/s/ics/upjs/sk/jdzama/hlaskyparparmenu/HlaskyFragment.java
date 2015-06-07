package s.ics.upjs.sk.jdzama.hlaskyparparmenu;


import android.app.Fragment;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HlaskyFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String ARG_INDEX = "INDEX";
    private int positionInList;
    private ListView hlaskyListView;
    private View rootView;
    private List<Song> myHlaskyList;
    private MediaPlayer player;
    private int songChosen;
    private boolean configChanged=false;

    public HlaskyFragment() {
        // Required empty public constructor
    }

    public static HlaskyFragment newInstance(int index){
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_INDEX, index);

        HlaskyFragment fragment = new HlaskyFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hlasky, container, false);

        player = new MediaPlayer();

        Bundle savedArguments = getArguments();
        if(savedArguments != null && savedArguments.containsKey(ARG_INDEX)) {
            positionInList = getArguments().getInt(ARG_INDEX);
        }
        setHlaskyIntoList();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void setHlaskyIntoList() {
        Log.wtf("FRAGMENT HLASKY: ","cislo je "+positionInList);

        if (HlaskyList.INSTANCE.hlaskyRefs==null){
            HlaskyList.INSTANCE.makeHlaskyRefs();
        }
        myHlaskyList = HlaskyList.INSTANCE.hlaskyRefs[positionInList];

        hlaskyListView = (ListView) rootView.findViewById(R.id.hlaskyListView);

        //http://stackoverflow.com/questions/11256563/how-to-set-both-lines-of-a-listview-using-simple-list-item-2
        ArrayAdapter<Song> hlaskyAdapter = new ArrayAdapter<Song>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, myHlaskyList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(myHlaskyList.get(position).getTitle());
                //text2.setTextColor(getResources().getColor(Color.DKGRAY));
                text2.setText(myHlaskyList.get(position).getArtist());
                return view;
            }
        };

        hlaskyListView.setAdapter(hlaskyAdapter);
        hlaskyListView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (player==null){
            player = new MediaPlayer();
        }
        player.reset();
        songChosen = myHlaskyList.get(i).getID();
        player = MediaPlayer.create(getActivity(), songChosen);
        player.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
        player.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        configChanged = false;
    }

    @Override
    public void onStop() {
        if (configChanged && player!=null){
            player.stop();
            player.release();
            player = null;
            Log.wtf("HLASKY FRAGM  ", "on stop a nulujem");
        }

        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        configChanged = true;

        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.wtf("FRAGMENT ITEM","som pred ifom");
        if (id == R.id.action_stop){
            if (player!=null){
                player.reset();
            }
            return true;
        }

        Log.wtf("FRAGMENT ITEM","som za ifom");
        return super.onOptionsItemSelected(item);
    }
}
