package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 *
 * Sue Smith - February 2014
 */

import java.util.Comparator;

public class Song implements Comparator<Song>, Comparable<Song>{

    private int id;
    public String title;
    public String artist;

    public Song() {
    }

    public Song(int songID, String songTitle, String songArtist){
        id=songID;
        title=songTitle;
        artist=songArtist;
    }

    public int getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}

    @Override
    public String toString() {
        return this.artist+" - "+this.title;
    }

    @Override
    public int compareTo(Song song) {
        return this.getID() - song.getID();
    }

    @Override
    public boolean equals(Object o) {
        if (o==null){
            return false;
        }
        if (!(o instanceof Song)){
            return false;
        }
        Song song2 = (Song) o;
        return this.getID()==song2.getID();
    }

    @Override
    public int compare(Song song1, Song song2) {
        return song1.getID() - song2.getID();
    }
}

