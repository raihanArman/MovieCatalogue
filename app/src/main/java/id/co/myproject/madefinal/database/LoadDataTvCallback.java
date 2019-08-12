package id.co.myproject.madefinal.database;

import android.database.Cursor;

import java.util.List;

import id.co.myproject.madefinal.model.TvShow;

public interface LoadDataTvCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
