package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;

import java.util.ArrayList;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.MusicService.MusicBinder;


public class MainActivity extends ActionBarActivity implements MediaPlayerControl {

    private MusicController controller;
    private MusicService musicService;

    private ArrayList<Song> songList;

    private boolean musicBound=false;
    private boolean paused=false, playbackPaused=false;

    private Intent playIntent;
    private Button hlaskyBtn;
    private View controlerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hlaskyBtn = (Button) findViewById(R.id.hlasky);
        controlerView = (View) findViewById(R.id.player_control);


        //nacitaj lasky
        getSongList();
        //nastav controller hudby
        //setController();

    }

    public void getSongList() {
        //nacitat piesne
        HlaskyList.INSTANCE.makeList();
        songList = HlaskyList.INSTANCE.hlaskyVsetky;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.wtf(null,"on start");
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
        Log.wtf(null,"player bound");
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;
            //get service
            musicService = binder.getService();
            //pass list
            musicService.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setController(){
        // controller up
        if (controller == null)
            controller = new MusicController(this, false);
        //listeners
        controller.setPrevNextListeners(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        },
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
        //playback

        controller.setMediaPlayer(this);
        controller.setAnchorView(controlerView);
        controller.setEnabled(true);
        Log.wtf(null,"controller");
        //controller.show(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void songPicked(View view){

        musicService.playSong();
        if(playbackPaused || controller == null){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused=true;

        SingletonData.INSTANCE.hide = true;
        controller.hide();

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }

    //////////////////////////////MUSIC PLAYER//////////////////////////////

    private void playNext(){
        musicService.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    private void playPrev(){
        musicService.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    public void start() {
        musicService.go();//.playSong();
        playbackPaused=false;
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicService.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicService!=null && musicBound && musicService.isPng())
            return musicService.getDur();
        else
            return 0;
    }

    @Override
    public int getCurrentPosition() {
        if(musicService!=null && musicBound && musicService.isPng())
            return musicService.getPosn();
        else
            return 0;
    }

    @Override
    public void seekTo(int i) {
        if (playbackPaused){
            musicService.go();
            musicService.seek(i);
        } else{
            musicService.seek(i);
        }

        //musicService.go();
    }

    @Override
    public boolean isPlaying() {
        if(musicService!=null && musicBound)
            return musicService.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
