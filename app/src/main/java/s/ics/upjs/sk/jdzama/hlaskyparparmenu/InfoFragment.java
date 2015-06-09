package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//podla http://developer.android.com/training/implementing-navigation/lateral.html

public class InfoFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String ARG_OBJECT = "object";
    private ArrayList<YoutubeLink> listLinks;
    private View rootView;
    private ArrayAdapter<YoutubeLink> gridAdapter;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        rootView = inflater.inflate(R.layout.fragment_info, container, false);

        makeLinksList();

        prepareAdapter();

        GridView gridViewVidea = (GridView) rootView.findViewById(R.id.youtubeGridView);
        gridViewVidea.setAdapter(gridAdapter);
        gridViewVidea.setOnItemClickListener(this);
        gridViewVidea.setOnItemLongClickListener(this);

        return rootView;
    }

    private void prepareAdapter() {
        gridAdapter = new ArrayAdapter<YoutubeLink>(rootView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, listLinks){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);

                text1.setGravity(Gravity.CENTER);
                text1.setText(listLinks.get(position).getName());
                view.setBackgroundColor(Color.RED);
                return view;
            }
        };
    }

    private void makeLinksList() {
        listLinks = new ArrayList<>();
        listLinks.add(new YoutubeLink("Video (20 min)","https://www.youtube.com/watch?v=-yTDnNWCUyk"));
        listLinks.add(new YoutubeLink("Video (9 min)","https://www.youtube.com/watch?v=bq6l8QfG9N8"));
        listLinks.add(new YoutubeLink("Video (35 min)","https://www.youtube.com/watch?v=j3VDFlYm5-A"));
        listLinks.add(new YoutubeLink("Video (10 min)","https://www.youtube.com/watch?v=wnXHQ_-9cKQ"));
        listLinks.add(new YoutubeLink("Video (7 min)","https://www.youtube.com/watch?v=kQMm3H3DEaE"));
        listLinks.add(new YoutubeLink("Video (4 min)","https://www.youtube.com/watch?v=trTvXgxY20M"));
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        YoutubeLink link = listLinks.get(i);
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getUriString()));
            if (intent.resolveActivity(rootView.getContext().getPackageManager()) != null) {
                startActivity(intent);
            }else{
                Toast.makeText(rootView.getContext(), "Nenašla sa vhodná aplikácia.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Toast.makeText(rootView.getContext(), "Nastala chyba.", Toast.LENGTH_SHORT).show();
        }
    }


    //podla https://www.codeofaninja.com/2013/02/android-share-intent-example.html
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        YoutubeLink link = listLinks.get(i);

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, link.getName());
        share.putExtra(Intent.EXTRA_TEXT, link.getUriString());

        startActivity(Intent.createChooser(share, "Zdieľať video!"));

        return true;
    }
}
