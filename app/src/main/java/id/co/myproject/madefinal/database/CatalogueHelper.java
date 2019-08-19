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
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.ID;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.POSTER;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.TITLE;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.TYPE;
import static id.co.myproject.madefinal.database.DatabaseContract.TABLE_CATALOGUE;

public class CatalogueHelper {
    public static final String TAG = CatalogueHelper.class.getSimpleName();
    public static final String DATABASE_TABLE = TABLE_CATALOGUE;
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

    public boolean cekFavorite(int id_movie, String type){
        String args = ID+" = "+id_movie+" and "+TYPE+" = '"+type+"'";
        Cursor cursor = database.query(DATABASE_TABLE, null,
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
        return database.query(DATABASE_TABLE, null,
                "type = 'movie'",
                null,
                null,
                null,
                _ID+" ASC",
                null);
    }

    public Cursor queryTvProvider(){
        return database.query(DATABASE_TABLE, null,
                "type = 'tv'",
                null,
                null,
                null,
                _ID+" ASC",
                null);
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id, String type){
        return database.delete(TABLE_CATALOGUE, ID+" = '"+id+"' and "+TYPE+" = '"+type+"'", null);
    }
}
