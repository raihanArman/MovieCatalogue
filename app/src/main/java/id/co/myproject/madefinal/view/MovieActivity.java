package id.co.myproject.madefinal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.adapter.MovieAdapter;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.request.ApiRequest;
import id.co.myproject.madefinal.request.RetrofitRequest;
import id.co.myproject.madefinal.viewmodel.MovieViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    MovieViewModel movieViewModel;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> movieModels;
    private ProgressDialog progressDialog;
    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.film));
        }

        movieModels = new ArrayList<>();
        movieModels.clear();
        recyclerView = findViewById(R.id.rv_movie);
        progressDialog = new ProgressDialog(this);
        adapter = new MovieAdapter(this);
        progressDialog.setMessage(getString(R.string.cek));
        progressDialog.show();
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
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
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getString(R.string.cari_movie));
            searchView.setOnQueryTextListener(this);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        movieModels.clear();
        adapter.notifyDataSetChanged();
        apiRequest.getSearchMovie(BuildConfig.API_KEY, newText)
                .enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            recyclerView.setVisibility(View.VISIBLE);
                            List<Movie> models = response.body().getMovieModels();
                            movieModels.addAll(models);
                            adapter.setMovieModelList(movieModels);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {

                    }
                });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}