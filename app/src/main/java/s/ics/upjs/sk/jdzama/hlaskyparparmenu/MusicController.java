package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.content.Context;
import android.widget.MediaController;

/**
 * Created by jDzama on 20.5.2015.
 */
public class MusicController extends MediaController {

    public MusicController(Context context) {
        super(context);
    }

    public MusicController(Context context, boolean show) {
        super(context, show);
    }

    @Override
    public void hide() {
        if (SingletonData.INSTANCE.hide){
            SingletonData.INSTANCE.hide = false;
            super.hide();
        }
    }
}
