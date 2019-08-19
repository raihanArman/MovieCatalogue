package id.co.myproject.favoritecatalogue.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "id.co.myproject.madefinal";
    private static final String SCHEME = "content";

    public DatabaseContract(){

    }

    public static String TABLE_CATALOGUE = "catalogue";
    public static final class CatalogueColumns implements BaseColumns{
        public static String ID = "id_catalogue";
        public static String TITLE = "title";
        public static String POSTER = "poster_path";
        public static String TYPE = "type";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_CATALOGUE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}