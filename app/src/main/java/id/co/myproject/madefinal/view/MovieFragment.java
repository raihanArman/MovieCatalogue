package id.co.myproject.madefinal.view;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.adapter.MovieAdapter;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> movieModels;
    private MovieViewModel movieViewModel;
    private ProgressDialog progressDialog;

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
        movieModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_movie);
        progressDialog = new ProgressDialog(getActivity());
        adapter = new MovieAdapter(getActivity());
        progressDialog.setMessage(getString(R.string.cek));
        progressDialog.show();

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        movieModels.clear();
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.mLiveData().removeObservers(this);
        movieViewModel.mLiveData().observe(this, new Observer<MovieResults>() {
            @Override
            public void onChanged(MovieResults movieResults) {
                if (movieViewModel.mLiveData().getValue() != null) {
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
                    List<Movie> models = movieResults.getMovieModels();
                    movieModels.addAll(models);
                    adapter.setMovieModelList(movieModels);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.pesan_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
