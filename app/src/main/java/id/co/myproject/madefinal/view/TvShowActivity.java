package id.co.myproject.madefinal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.adapter.TvShowAdapter;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.model.TvShowResults;
import id.co.myproject.madefinal.request.ApiRequest;
import id.co.myproject.madefinal.request.RetrofitRequest;
import id.co.myproject.madefinal.viewmodel.TvShowViewModel;
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

public class TvShowActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private TvShowAdapter adapter;
    private List<TvShow> tvShowList;
    private TvShowViewModel tvShowViewModel;
    private ProgressDialog progressDialog;
    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.tv));
        }

        tvShowList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_tv);
        progressDialog = new ProgressDialog(this);
        adapter = new TvShowAdapter(this);
        progressDialog.setMessage(getString(R.string.cek));
        progressDialog.show();

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.mLiveDataTv().removeObservers(this);
        tvShowViewModel.mLiveDataTv().observe(this, new Observer<TvShowResults>() {
            @Override
            public void onChanged(TvShowResults tvShowResults) {
                if (tvShowViewModel.mLiveDataTv().getValue() != null) {
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
                    List<TvShow> models = tvShowResults.getTvShowModels();
                    tvShowList.addAll(models);
                    adapter.setTvShows(tvShowList);
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
            searchView.setQueryHint(getString(R.string.cari_tv));
            searchView.setOnQueryTextListener(this);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        tvShowList.clear();
        adapter.notifyDataSetChanged();
        apiRequest.getSearchTv(BuildConfig.API_KEY, newText)
                .enqueue(new Callback<TvShowResults>() {
                    @Override
                    public void onResponse(Call<TvShowResults> call, Response<TvShowResults> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            recyclerView.setVisibility(View.VISIBLE);
                            List<TvShow> models = response.body().getTvShowModels();
                            tvShowList.addAll(models);
                            adapter.setTvShows(tvShowList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShowResults> call, Throwable t) {

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
