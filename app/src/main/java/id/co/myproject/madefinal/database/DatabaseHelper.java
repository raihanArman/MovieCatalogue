package id.co.myproject.madefinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbcatalogue";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
            +"(%s INTEGER PRIMARY KEY, "
            +" %s TEXT NOT NULL, "
            +" %s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.CatalogueColumns._ID,
            DatabaseContract.CatalogueColumns.TITLE,
            DatabaseContract.CatalogueColumns.POSTER
    );

    public static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY, "
                    +" %s TEXT NOT NULL, "
                    +" %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TV,
            DatabaseContract.CatalogueColumns._ID,
            DatabaseContract.CatalogueColumns.TITLE,
            DatabaseContract.CatalogueColumns.POSTER
            );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_TV);
        onCreate(sqLiteDatabase);
    }
}
