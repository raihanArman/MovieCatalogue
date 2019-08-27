package id.co.myproject.madefinal.util;

import android.database.Cursor;

import java.util.ArrayList;

import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.TvShow;

import static android.provider.BaseColumns._ID;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.POSTER;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.TITLE;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorMovie(Cursor cursor){
        ArrayList<Movie> movies = new ArrayList<>();
        Movie model;
        if (cursor.moveToFirst()) {
            do {
                model = new Movie();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                model.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movies.add(model);
            } while (cursor.moveToNext());
        }
        return movies;
    }

    public static ArrayList<TvShow> mapCursorTv(Cursor cursor){
        ArrayList<TvShow> tvShows = new ArrayList<>();
        TvShow model;
        if(cursor.moveToFirst()) {
            do {
                model = new TvShow();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                model.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                model.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                tvShows.add(model);
            } while (cursor.moveToNext());
        }
        return tvShows;
    }
}
