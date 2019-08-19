package id.co.myproject.madefinal.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.adapter.FavoriteTvAdapter;
import id.co.myproject.madefinal.database.CatalogueHelper;
import id.co.myproject.madefinal.database.LoadDataTvCallback;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.TvShow;

import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.CONTENT_URI;
import static id.co.myproject.madefinal.util.MappingHelper.mapCursorMovie;
import static id.co.myproject.madefinal.util.MappingHelper.mapCursorTv;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements LoadDataTvCallback {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    FavoriteTvAdapter adapter;
    CatalogueHelper helper;
    private static final String EXTRA_STATE = "EXTRA_STATE";


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_fav_tv);
        progressDialog = new ProgressDialog(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        helper = CatalogueHelper.getINSTANCE(getActivity());
        helper.open();

        adapter = new FavoriteTvAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null){
            new LoadTvAsync(getActivity(), this).execute();
        }else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapter.setTvShowModelArrayList(list);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getTvShowModelArrayList());
    }

    @Override
    public void preExecute() {
        progressDialog.setMessage(getString(R.string.cek));
        progressDialog.show();
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressDialog.dismiss();
        ArrayList<TvShow> listTv = mapCursorTv(cursor);
        if (listTv.size() > 0){
            adapter.setTvShowModelArrayList(listTv);
        }else {
            adapter.setTvShowModelArrayList(new ArrayList<TvShow>());
        }
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, Cursor>{

        private WeakReference<Context> weakContext;
        private WeakReference<LoadDataTvCallback> weakCallback;

        private LoadTvAsync(Context context, LoadDataTvCallback callback){
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
            return context.getContentResolver().query(Uri.parse(CONTENT_URI+"/tv"), null, null, null, null);
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