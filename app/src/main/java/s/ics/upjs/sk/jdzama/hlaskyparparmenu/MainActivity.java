package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;

import java.util.ArrayList;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.MusicService.MusicBinder;


public class MainActivity extends Activity implements MediaPlayerControl {

    private MusicController controller;
    private MusicService musicService;

    private ArrayList<Song> songList;

    private boolean musicBound=false;
    private boolean paused=false, playbackPaused=false;

    private Intent playIntent;
    private View controlerView;

    public final String SAVED_STATE = "savedState";
    private SaveData data;
    private TextView songInfo;

    private String adviceToPlay="";

    public static final boolean DO_NOT_READ_AGAIN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, DO_NOT_READ_AGAIN);

        getActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_main);

        controlerView = findViewById(R.id.player_control);
        songInfo = (TextView) findViewById(R.id.songInfo);
        adviceToPlay = getResources().getString(R.string.hint);
        setTextView();

        IntentFilter intentFilter = new IntentFilter("s.ics.upjs.sk.jdzama.hlaskyparparmenu.TRACK_INFO_INTENT");
        registerReceiver(myReceiver, intentFilter);

        //nacitaj hlasky
        getSongList();

        if(savedInstanceState == null) {
            //nic
        } else {
            restoreMusicState(savedInstanceState);
        }
    }

    private void setTextView(){
        songInfo.setGravity(Gravity.CENTER);
        songInfo.setText(adviceToPlay);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState == null) {
            //nic
        } else {
            restoreMusicState(savedInstanceState);
        }
    }

    private void restoreMusicState(Bundle savedInstanceState) {
        data = (SaveData) savedInstanceState.get(SAVED_STATE);
        //prepareService();
    }

    public void getSongList() {
        //nacitat piesne
        HlaskyList.INSTANCE.makeList();
        songList = HlaskyList.INSTANCE.hlaskyVsetky;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onResume(){
        super.onResume();

        if(musicBound && !paused){
            setController();
            paused=false;
            start();
            controller.show(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {


        if (musicService!= null && musicService.getPosn() < musicService.getDur()){
            data = new SaveData(musicService.getSong(), musicService.getPosn());
            outState.putSerializable(SAVED_STATE, data);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause(){
        paused=true;

        super.onPause();
    }

    @Override
    protected void onStop() {
        stopPlaying();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    private void stopPlaying(){
        if (controller != null){
            SingletonData.INSTANCE.hide = true;
            controller.hide();

            controller=null;
        }
        songInfo.setText(adviceToPlay);

        if (musicBound) {
            try {
                unbindService(musicConnection);
            }catch (IllegalArgumentException e){
                //Log.wtf("ACTIVITY : ","nestiha, zrusi sa (pokus o viacnasobne zrusenie)");
            }
        }

        musicService = null;
        //stopService(playIntent);
        playIntent = null;
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

        controller.setMediaPlayer(this);
        controller.setAnchorView(controlerView);
        controller.setEnabled(true);
    }
    private void prepareService(){
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            //startService(playIntent);
        }
    }

    public void startPlaying(){
        if (data==null){
            musicService.playSong();
        }else {
            musicService.playSongAtPos(data.getSongId(), data.getPosition());
            data = null;
        }
        if(playbackPaused || controller == null){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
        setTrackInfo();
    }

    public void setTrackInfo(){
        songInfo.setText(musicService.getSongAuthor()+" - "+musicService.getSongTitle());
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
            startPlaying();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_stop) {
            if (musicBound){
                stopPlaying();
            }
            return true;
        }
        if (id == R.id.action_play) {
            prepareService();
            return true;
        }
        if (id == R.id.action_preferences) {
            startPreferences();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTrackInfo();
        }};

    public void startHlaskyActivity(View view){
        Intent hlaskyActivityIntent = new Intent(MainActivity.this, HlaskyActivity.class);
        MainActivity.this.startActivity(hlaskyActivityIntent);
    }

    public void startOblubeneActivity(View view){
        Intent oblubeneActivityIntent = new Intent(MainActivity.this, OblubeneActivity.class);
        MainActivity.this.startActivity(oblubeneActivityIntent);
    }

    public void startInfoActivity(View view){
        Intent infoActivityIntent = new Intent(MainActivity.this, InfoActivity.class);
        MainActivity.this.startActivity(infoActivityIntent);
    }

    public void startPreferences(){
        Intent preferencesActivityIntent = new Intent(MainActivity.this, PreferencesActivity.class);
        MainActivity.this.startActivity(preferencesActivityIntent);
    }

    //////////////////////////////MUSIC PLAYER//////////////////////////////

    private void playNext(){
        musicService.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
        setTrackInfo();
    }

    private void playPrev(){
        musicService.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
        setTrackInfo();
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