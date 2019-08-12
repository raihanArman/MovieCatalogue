package id.co.myproject.madefinal.database;

import android.database.Cursor;

import java.util.List;

import id.co.myproject.madefinal.model.Movie;

public interface LoadDataMovieCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
