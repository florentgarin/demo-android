package com.training.firstapp.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.training.firstapp.database.WineDBHelper;

import java.util.Arrays;
import java.util.List;

public class WineContentProvider extends ContentProvider {

    private WineDBHelper dbHelper;
    private SQLiteDatabase database;

    // content://wine/bottle
    // content://wine/bottle/23
    // content://contacts/people/23/photos/12

    private final static int BOTTLE_MULTIPLE = 1;
    private final static int BOTTLE_UNI = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI("wine", "bottle", BOTTLE_MULTIPLE);
        sURIMatcher.addURI("wine", "bottle/#", BOTTLE_UNI);
    }

    public WineContentProvider() {
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

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public boolean onCreate() {
        dbHelper = new WineDBHelper(getContext());
        database = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        var code = sURIMatcher.match(uri);
        switch (code) {
            //content://wine/bottle/56
            case BOTTLE_UNI:
                String id = uri.getLastPathSegment();
                String select;
                String[] selectArgs;
                if(selection != null && !selection.isEmpty()) {
                    select = selection + " and id = ?";
                    List<String> lst = Arrays.asList(selectionArgs);
                    lst.add(id);
                    selectArgs = lst.toArray(new String[selectionArgs.length + 1]);
                }
                 else {
                    select = "id = ?";
                    selectArgs = new String[]{id};
                }
                return database.query("bottle", projection, select, selectArgs, null, null, sortOrder);
            //content://wine/bottle
            case BOTTLE_MULTIPLE:
                return database.query("bottle", projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalStateException();

        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}