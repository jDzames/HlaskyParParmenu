package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import java.util.ArrayList;
import java.util.Random;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 *
 * Sue Smith - February 2014
 */

public class MusicService extends Service implements
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    //media player
    private MediaPlayer player;
    //song list
    private ArrayList<Song> songs;
    //current position
    private int songPosn;
    //binder
    private final IBinder musicBind = new MusicBinder();
    //title and author of current song
    private String songTitle="";
    private String songAuthor="";
    //notification id
    public static final int NOTIFY_ID=1;
    //shuffle flag and random
    private boolean shuffle=true;
    private Random rand;

    public void onCreate(){
        //create the service
        super.onCreate();
        //random
        rand=new Random();
        //initialize position
        songPosn=rand.nextInt(102);

        //create player
        player = new MediaPlayer();
        //initialize
        initMusicPlayer();
    }

    public void initMusicPlayer(){
        //set player properties
        player.setWakeMode(getApplicationContext(),PowerManager.PARTIAL_WAKE_LOCK);
        //player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //set listeners
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        //player.setOnPreparedListener(this);
        //playSong();
    }

    //pass song list
    public void setList(ArrayList<Song> theSongs){
        songs=theSongs;
    }

    public void playSongAtPos(int songId, int position) {
        //play
        if (player == null){
            player = new MediaPlayer();
            //initialize
            initMusicPlayer();
        }
        player.reset();
        //get song
        if (songs==null){
            if(HlaskyList.INSTANCE.hlaskyVsetky==null){
                HlaskyList.INSTANCE.makeList();
            }
            songs = HlaskyList.INSTANCE.hlaskyVsetky;
        }
        songPosn = songId;
        Song playSong = songs.get(songPosn);
        //get title and author
        songTitle=playSong.getTitle();
        songAuthor = playSong.getArtist();
        //get id
        long currSong = playSong.getID();
        try{
            player = MediaPlayer.create(this, (int)currSong);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.start();
        player.seekTo(position);
        //notification();
        player.setOnCompletionListener(this);
    }

    //binder
    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    //activity will bind to service
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    //release resources when unbind
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        player = null;
        stopSelf();
        return false;
    }

    //play a song
    public void playSong(){
        //play
        if (player == null){
            player = new MediaPlayer();
            //initialize
            initMusicPlayer();
        }
        player.reset();
        //get song
        if (songs==null){
            if(HlaskyList.INSTANCE.hlaskyVsetky==null){
                HlaskyList.INSTANCE.makeList();
            }
            songs = HlaskyList.INSTANCE.hlaskyVsetky;
        }
        Song playSong = songs.get(songPosn);
        //get title and author
        songTitle=playSong.getTitle();
        songAuthor = playSong.getArtist();
        //get id
        long currSong = playSong.getID();
        try{
            player = MediaPlayer.create(this, (int)currSong);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.start();
        //notification();
        player.setOnCompletionListener(this);
    }

    //set the song
    public void setSong(int songIndex){
        songPosn=songIndex;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.wtf("MUSIC SERVICE", "COMPLETED");
        //check if playback has reached the end of a track
        mp.reset();
        playNext();
        broadcastIntent(null);
    }

    public void broadcastIntent(View view)
    {
        Intent intent = new Intent();
        intent.setAction("s.ics.upjs.sk.jdzama.hlaskyparparmenu.TRACK_INFO_INTENT");
        sendBroadcast(intent);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.v("MUSIC PLAYER", "Playback Error");
        mp.reset();
        return false;
    }

    /*@Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        //mp.start();

        //notification();
    }*/

    private void notification(){
        //notification
        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification not = builder.getNotification();
        startForeground(NOTIFY_ID, not);
    }

    public String getSongTitle(){
        Log.wtf("SERVICE TITLE           ", "je " + songTitle.toString()+" cislo je  "+songs.size());
        if (songTitle==null){
            songTitle="";
        }
        return songTitle;
    }

    public String getSongAuthor(){
        Log.wtf("SERVICE AUTHOR           ","je "+songAuthor.toString());
        if (songAuthor==null){
            songAuthor="";
        }
        return songAuthor;
    }

    public int getSong(){
        return songPosn;
    }

    //playback methods
    public int getPosn(){
        return player.getCurrentPosition();
    }

    public int getDur(){
        return player.getDuration();
    }

    public boolean isPng(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
        Log.wtf("POSITION: ", "" + player.getCurrentPosition());
    }

    public void go(){
        player.start();
    }

    //skip to previous track
    public void playPrev(){
        int newSong = songPosn;
        while(newSong==songPosn){
            newSong=rand.nextInt(songs.size());
        }
        songPosn=newSong;

        playSong();
    }

    //skip to next
    public void playNext(){
        int newSong = songPosn;
        while(newSong==songPosn){
            newSong=rand.nextInt(songs.size());
        }
        songPosn=newSong;

        playSong();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }

}

