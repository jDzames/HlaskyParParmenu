package s.ics.upjs.sk.jdzama.hlaskyparparmenu;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment{


    public static final String BANDA = "http://www.bandatrotlu.com/parparmenu/";
    public static final String WIKI = "http://cs.wikipedia.org/wiki/P%C3%A1r_Pa%C5%99men%C5%AF";
    public static final String FB = "https://www.facebook.com/ParParmenu";
    public static final String NECYKLOPEDIA = "http://necyklopedie.wikia.com/wiki/P%C3%A1r_Pa%C5%99men%C5%AF";

    private View rootView;

    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_web, container, false);

        Button banda = (Button) rootView.findViewById(R.id.banda);
        banda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = BANDA;
                sendDataAndShow(link);
            }
        });

        Button wiki = (Button) rootView.findViewById(R.id.wiki);
        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = WIKI;
                sendDataAndShow(link);
            }
        });
        Button fb = (Button) rootView.findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = FB;
                sendDataAndShow(link);
            }
        });
        Button necyklopedia = (Button) rootView.findViewById(R.id.necyklopedia);
        necyklopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = NECYKLOPEDIA;
                sendDataAndShow(link);
            }
        });


        return rootView;
    }


    private void sendDataAndShow(String link){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        if (intent.resolveActivity(rootView.getContext().getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Toast.makeText(rootView.getContext(), "Nenašla sa vhodná aplikácia.", Toast.LENGTH_SHORT).show();
        }
    }

}
