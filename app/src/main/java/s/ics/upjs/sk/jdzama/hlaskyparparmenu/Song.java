package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 *
 * Sue Smith - February 2014
 */

public class Song {

    private int id;
    public String title;
    public String artist;

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
}

