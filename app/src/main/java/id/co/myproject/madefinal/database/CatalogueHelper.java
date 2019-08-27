package id.co.myproject.madefinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.TvShow;

import static android.provider.BaseColumns._ID;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.POSTER;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.TITLE;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.TYPE;
import static id.co.myproject.madefinal.database.DatabaseContract.TABLE_MOVIE;
import static id.co.myproject.madefinal.database.DatabaseContract.TABLE_TV;

public class CatalogueHelper {
    public static final String TAG = CatalogueHelper.class.getSimpleName();
    public static final String DATABASE_TABLE_MOVIE = TABLE_MOVIE;
    public static final String DATABASE_TABLE_TV = TABLE_TV;
    public static DatabaseHelper databaseHelper;
    private static CatalogueHelper INSTANCE;

    private static SQLiteDatabase database;
    Context context;

    private CatalogueHelper(Context context){
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public static CatalogueHelper getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new CatalogueHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public boolean cekFavoriteMovie(int id_movie){
        String args = _ID+" = "+id_movie;
        Cursor cursor = database.query(DATABASE_TABLE_MOVIE, null,
                args,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }

    public boolean cekFavoriteTV(int id_tv){
        String args = _ID+" = "+id_tv;
        Cursor cursor = database.query(DATABASE_TABLE_TV, null,
                args,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }

    public Cursor queryMovieProvider(){
        return database.query(DATABASE_TABLE_MOVIE, null,
                null,
                null,
                null,
                null,
                _ID+" ASC",
                null);
    }

    public Cursor queryTvProvider(){
        return database.query(DATABASE_TABLE_TV, null,
                null,
                null,
                null,
                null,
                _ID+" ASC",
                null);
    }

    public long insertProviderMovie(ContentValues values){
        return database.insert(DATABASE_TABLE_MOVIE, null, values);
    }

    public long insertProviderTV(ContentValues values){
        return database.insert(DATABASE_TABLE_TV, null, values);
    }

    public int deleteProviderMovie(String id){
        return database.delete(TABLE_MOVIE, _ID+" = '"+id+"'", null);
    }

    public int deleteProviderTV(String id){
        return database.delete(TABLE_TV, _ID+" = '"+id+"'", null);
    }
}
