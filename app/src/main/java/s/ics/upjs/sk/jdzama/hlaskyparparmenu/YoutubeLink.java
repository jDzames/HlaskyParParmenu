package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

/**
 * Created by jDzama on 9.6.2015.
 */
public class YoutubeLink {

    public String name;
    public String uriString;

    public YoutubeLink() {
    }

    public YoutubeLink(String name, String uriString) {
        this.name = name;
        this.uriString = uriString;
    }

    public String getName() {
        return name;
    }

    public String getUriString() {
        return uriString;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }
}
