package id.co.myproject.madefinal.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.adapter.FavoriteMovieAdapter;
import id.co.myproject.madefinal.database.CatalogueHelper;
import id.co.myproject.madefinal.database.LoadDataMovieCallback;
import id.co.myproject.madefinal.model.Movie;

import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.CONTENT_URI_MOVIE;
import static id.co.myproject.madefinal.util.MappingHelper.mapCursorMovie;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadDataMovieCallback{

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    FavoriteMovieAdapter adapter;
    CatalogueHelper helper;
    TextView tvMovieEmpty;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        recyclerView = view.findViewById(R.id.rv_fav_movie);
        tvMovieEmpty = view.findViewById(R.id.tv_movie_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        tvMovieEmpty.setVisibility(View.INVISIBLE);
        helper = CatalogueHelper.getINSTANCE(getActivity());
        helper.open();

        adapter = new FavoriteMovieAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null){
            new LoadMovieAsync(getActivity(), this).execute();
        }else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                tvMovieEmpty.setVisibility(View.INVISIBLE);
                adapter.setMovieModelList(list);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getMovieModelList());
    }

    @Override
    public void preExecute() {
        progressDialog.setMessage(getString(R.string.cek));
        progressDialog.show();
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressDialog.dismiss();
        List<Movie> listMovie = mapCursorMovie(cursor);
        if (listMovie.size() > 0){
            tvMovieEmpty.setVisibility(View.INVISIBLE);
            adapter.setMovieModelList(listMovie);
        }else {
            tvMovieEmpty.setVisibility(View.VISIBLE);
            adapter.setMovieModelList(new ArrayList<Movie>());
        }
    }

    public static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor>{

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadDataMovieCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadDataMovieCallback callback){
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
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
    public void onDestroyView() {
        super.onDestroyView();
        helper.close();
    }
}
