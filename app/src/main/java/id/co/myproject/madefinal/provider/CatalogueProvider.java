package id.co.myproject.madefinal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import id.co.myproject.madefinal.database.CatalogueHelper;

import static id.co.myproject.madefinal.database.DatabaseContract.AUTHORITY;
import static id.co.myproject.madefinal.database.DatabaseContract.TABLE_CATALOGUE;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.CONTENT_URI;

public class CatalogueProvider extends ContentProvider {
    private static final int CATALOGUE_MOVIE = 1;
    private static final int CATALOGUE_TV = 2;
    private static final int CATALOGUE_MOVIE_ID = 3;
    private static final int CATALOGUE_TV_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private CatalogueHelper helper;

    static{
        sUriMatcher.addURI(AUTHORITY, TABLE_CATALOGUE+"/movie",CATALOGUE_MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_CATALOGUE+"/tv", CATALOGUE_TV);
        sUriMatcher.addURI(AUTHORITY, TABLE_CATALOGUE+"/#", CATALOGUE_MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_CATALOGUE+"/#", CATALOGUE_TV_ID);
    }


    @Override
    public boolean onCreate() {
        helper = CatalogueHelper.getINSTANCE(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        helper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case CATALOGUE_MOVIE:
                cursor = helper.queryMovieProvider();
                break;
            case CATALOGUE_TV:
                cursor = helper.queryTvProvider();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        helper.open();
        long added;
        switch (sUriMatcher.match(uri)){
            case CATALOGUE_MOVIE:
            case CATALOGUE_TV :
                added = helper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        helper.open();
        int deleted;
        switch (sUriMatcher.match(uri)){
            case CATALOGUE_MOVIE_ID:
                deleted = helper.deleteProvider(uri.getLastPathSegment(), "movie");
                break;
            case CATALOGUE_TV_ID:
                deleted = helper.deleteProvider(uri.getLastPathSegment(), "tv");
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
