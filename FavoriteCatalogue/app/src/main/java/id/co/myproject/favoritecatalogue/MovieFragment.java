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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import id.co.myproject.favoritecatalogue.adapter.MovieAdapter;
import id.co.myproject.favoritecatalogue.model.Movie;

import static id.co.myproject.favoritecatalogue.db.DatabaseContract.CatalogueColumns.CONTENT_URI_MOVIE;
import static id.co.myproject.favoritecatalogue.util.MappingHelper.mapCursorMovie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements LoadDataCallback{

    RecyclerView recyclerView;
    List<Movie> movies;
    MovieAdapter adapter;
    TextView tvMovieEmpty;
    private static final String EXTRA_STATE = "EXTRA_STATE";
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
        tvMovieEmpty = view.findViewById(R.id.tv_movie_empty);
        tvMovieEmpty.setVisibility(View.INVISIBLE);
        adapter = new MovieAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        if (savedInstanceState == null){
            new getData(getActivity(), this).execute();
        }else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                tvMovieEmpty.setVisibility(View.INVISIBLE);
                adapter.setMovieModelList(list);
            }
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        ArrayList<Movie> listMovie = mapCursorMovie(cursor);
        if (listMovie.size() > 0){
            tvMovieEmpty.setVisibility(View.INVISIBLE);
            adapter.setMovieModelList(listMovie);
        }else {
            tvMovieEmpty.setVisibility(View.VISIBLE);
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
            return context.getContentResolver().query(Uri.parse(CONTENT_URI_MOVIE+"/movie"), null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getMovieModelList());
    }
}
