package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.KeyEvent;
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
        /*super.hide();*/
        if (SingletonData.INSTANCE.hide){
            Log.wtf("CONTROLLER: ", "som v HIDE ");
            SingletonData.INSTANCE.hide = false;
            super.hide();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            super.hide();
            Log.wtf("CONTROLLER: ", "som v SPAT ");
            ((Activity) getContext()).finish();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        super.hide();
        Log.wtf("CONTROLLER: ", "som v ROTATE ");
        // ((Activity) getContext()).finish();
    }
}