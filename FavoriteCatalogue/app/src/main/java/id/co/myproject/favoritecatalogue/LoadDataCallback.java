package id.co.myproject.favoritecatalogue;

import android.database.Cursor;

public interface LoadDataCallback {
    void postExecute(Cursor cursor);
}
