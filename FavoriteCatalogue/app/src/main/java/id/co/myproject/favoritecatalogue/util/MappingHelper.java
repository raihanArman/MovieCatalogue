package id.co.myproject.favoritecatalogue.util;

import android.database.Cursor;

import java.util.ArrayList;

import id.co.myproject.favoritecatalogue.model.Movie;
import id.co.myproject.favoritecatalogue.model.TvShow;

import static id.co.myproject.favoritecatalogue.db.DatabaseContract.CatalogueColumns.ID;
import static id.co.myproject.favoritecatalogue.db.DatabaseContract.CatalogueColumns.POSTER;
import static id.co.myproject.favoritecatalogue.db.DatabaseContract.CatalogueColumns.TITLE;


public class MappingHelper {
    public static ArrayList<Movie> mapCursorMovie(Cursor cursor){
        ArrayList<Movie> movies = new ArrayList<>();
        Movie model;
        cursor.moveToFirst();
        do {
            model = new Movie();
            model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
            model.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
            model.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
            movies.add(model);
        }while (cursor.moveToNext());
        return movies;
    }

    public static ArrayList<TvShow> mapCursorTv(Cursor cursor){
        ArrayList<TvShow> tvShows = new ArrayList<>();
        TvShow model;
        cursor.moveToFirst();
        do {
            model = new TvShow();
            model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
            model.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
            model.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
            tvShows.add(model);
        } while (cursor.moveToNext());
        return tvShows;
    }
}