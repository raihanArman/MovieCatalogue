package id.co.myproject.favoritecatalogue.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "id.co.myproject.madefinal";
    private static final String SCHEME = "content";

    public DatabaseContract(){

    }

    public static String TABLE_MOVIE = "movie_favorite";
    public static String TABLE_TV = "tv_favorite";
    public static final class CatalogueColumns implements BaseColumns{
        public static String TITLE = "title";
        public static String POSTER = "poster_path";
        public static String TYPE = "type";

        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}