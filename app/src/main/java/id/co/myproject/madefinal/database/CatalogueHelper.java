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

    public List<Movie> getAllMovie(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                "type = 'movie'",
                null,
                null,
                null,
                _ID+" ASC",
                null);
        cursor.moveToFirst();
        Log.d(TAG, "getAllMovie: "+cursor.getCount());
        Movie model;
        if (cursor.getCount() > 0){
            do {
                model = new Movie();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                model.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                arrayList.add(model);
            } while (cursor.moveToNext());
            Log.d(TAG, "getAllMovieList: "+arrayList.size());
        }
        cursor.close();
        return arrayList;
    }

    public List<TvShow> getAllTvShow(){
        List<TvShow> tvShowList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                "type = 'tv'",
                null,
                null,
                null,
                _ID+" ASC",
                null);
        cursor.moveToFirst();
        TvShow model;
        if (cursor.getCount() > 0){
            do {
                model = new TvShow();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                model.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                tvShowList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tvShowList;
    }

    public long insertMovie(Movie movie){
        ContentValues args = new ContentValues();
        args.put(ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(POSTER, movie.getPosterPath());
        args.put(TYPE, "movie");
        return database.insert(DATABASE_TABLE, null, args);
    }

    public long insertTvShow(TvShow tvShow){
        ContentValues args = new ContentValues();
        args.put(ID, tvShow.getId());
        args.put(TITLE, tvShow.getName());
        args.put(POSTER, tvShow.getPosterPath());
        args.put(TYPE, "tv");
        return database.insert(DATABASE_TABLE, null, args);
    }

    public long deleteMovieTv(int id, String type){
        return database.delete(TABLE_CATALOGUE, ID+" = '"+id+"' and "+TYPE+" = '"+type+"'", null);
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
}
