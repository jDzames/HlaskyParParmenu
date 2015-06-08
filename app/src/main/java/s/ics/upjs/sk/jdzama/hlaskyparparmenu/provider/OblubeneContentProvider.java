package s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import s.ics.upjs.sk.jdzama.hlaskyparparmenu.database.DatabaseOpenHelper;
import s.ics.upjs.sk.jdzama.hlaskyparparmenu.util.Defaults;

import static android.content.ContentResolver.SCHEME_CONTENT;
import static s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.Provider.Oblubene;

public class OblubeneContentProvider extends ContentProvider {

    public static final String AUTHORITY = "s.ics.upjs.sk.jdzama.hlaskyparparmenu.provider.OblubeneContentProvider";
    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme(SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(Oblubene.TABLE_NAME)
            .build();

    private DatabaseOpenHelper databaseHelper;

    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int URI_MATCH_NOTES = 0;
    private static final int URI_MATCH_NOTE_BY_ID = 1;

    public OblubeneContentProvider() {
    }

    @Override
    public boolean onCreate() {
        this.databaseHelper = new DatabaseOpenHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(Oblubene.TABLE_NAME, Defaults.ALL_COLUMNS,
                Defaults.NO_SELECTION, Defaults.NO_SELECTION_ARGS, Defaults.NO_GROUP_BY,
                Defaults.NO_HAVING, Defaults.NO_SORT_ORDER);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
