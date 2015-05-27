package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import java.io.Serializable;

/**
 * Created by jDzama on 27.5.2015.
 */
public class SaveData implements Serializable{

    private static final long serialVersionUID = 4154020326112224920L;
    public int songId;
    public int position;

    public SaveData(int songId, int position) {
        this.songId = songId;
        this.position = position;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
