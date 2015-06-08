package s.ics.upjs.sk.jdzama.hlaskyparparmenu.database;

//podla ics.upjs.sk/~novotnyr/android/5-stretnutie-zlte-poznamkove-papieriky.html

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.HlaskyList;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.Song;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.Provider;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.util.Defaults;

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "oblubene";
    public static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, Defaults.DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSql());
        insertSampleEntry(db, HlaskyList.INSTANCE.hlaskyRozne.get(0));
        insertSampleEntry(db, HlaskyList.INSTANCE.hlaskyRozne.get(1));
    }

    private String createTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s INTEGER,"
                + "%s TEXT,"
                + "%s TEXT"
                + ")";
        return String.format(sqlTemplate,
                Provider.Oblubene.TABLE_NAME,
                Provider.Oblubene._ID,
                Provider.Oblubene.RAW_ID,
                Provider.Oblubene.NAZOV,
                Provider.Oblubene.AUTOR);
    }

    private void insertSampleEntry(SQLiteDatabase db, Song song) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Provider.Oblubene.RAW_ID, song.getID());
        contentValues.put(Provider.Oblubene.NAZOV, song.getTitle());
        contentValues.put(Provider.Oblubene.AUTOR, song.getArtist());
        db.insert(Provider.Oblubene.TABLE_NAME, Defaults.NO_NULL_COLUMN_HACK, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //nerobim nic, databaza sa nemeni
    }
}
