package s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider;

import android.provider.BaseColumns;

//samozrejme z ics.upjs.sk/~novotnyr/android/5-stretnutie-zlte-poznamkove-papieriky.html

public interface Provider {
    public interface Oblubene extends BaseColumns {
        public static final String TABLE_NAME = "oblubene";

        public static final String RAW_ID = "rawid";

        public static final String NAZOV = "nazov";

        public static final String AUTOR = "autor";
    }
}
