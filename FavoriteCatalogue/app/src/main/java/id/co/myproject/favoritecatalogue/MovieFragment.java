package id.co.myproject.favoritecatalogue;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import id.co.myproject.favoritecatalogue.adapter.MovieAdapter;
import id.co.myproject.favoritecatalogue.model.Movie;

import static id.co.myproject.favoritecatalogue.db.DatabaseContract.CatalogueColumns.CONTENT_URI;
import static id.co.myproject.favoritecatalogue.util.MappingHelper.mapCursorMovie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements LoadDataCallback{

    RecyclerView recyclerView;
    List<Movie> movies;
    MovieAdapter adapter;
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movies = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_fav_movie);
        adapter = new MovieAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        new getData(getActivity(), this).execute();
    }

    @Override
    public void postExecute(Cursor cursor) {
        ArrayList<Movie> listMovie = mapCursorMovie(cursor);
        if (listMovie.size() > 0){
            adapter.setMovieModelList(listMovie);
        }else {
            Toast.makeText(getActivity(), "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            adapter.setMovieModelList(new ArrayList<Movie>());
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor>{

        private WeakReference<Context> weakContext;
        private WeakReference<LoadDataCallback> weakCallback;

        private getData(Context context, LoadDataCallback callback){
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(Uri.parse(CONTENT_URI+"/movie"), null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }
}
