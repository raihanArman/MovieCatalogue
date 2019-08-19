package id.co.myproject.madefinal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.R;
import id.co.myproject.madefinal.database.CatalogueHelper;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.request.ApiRequest;
import id.co.myproject.madefinal.request.RetrofitRequest;
import id.co.myproject.madefinal.viewmodel.DetailMovieViewModel;
import id.co.myproject.madefinal.viewmodel.DetailMovieViewModelFactory;
import id.co.myproject.madefinal.viewmodel.DetailTvViewModel;
import id.co.myproject.madefinal.viewmodel.DetailTvViewModelFactory;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.CONTENT_URI;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.ID;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.POSTER;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.TITLE;
import static id.co.myproject.madefinal.database.DatabaseContract.CatalogueColumns.TYPE;

public class DetailActivity extends AppCompatActivity {
    public static final int EXTRAS_DETAIL_MOVIE = 1;
    public static final int EXTRAS_DETAIL_TV = 2;

    private ImageView iv_poster, iv_poster_background, iv_back;
    private TextView tv_rilis, tv_rating, tv_deskripsi, tv_title;
    private FloatingActionButton fb_favorit;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressDialog progressDialog;
    private int detail;
    private int id_movie;
    private int id_tv;
    Movie movieModel;
    TvShow tvShowModel;
    ApiRequest apiRequest;
    DetailMovieViewModel viewModelMovie;
    DetailTvViewModel viewModelTv;
    CatalogueHelper helper;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        movieModel = new Movie();
        tvShowModel = new TvShow();
        helper = CatalogueHelper.getINSTANCE(this);
        helper.open();
        coordinatorLayout = findViewById(R.id.layout);

        coordinatorLayout.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.cek));
        progressDialog.show();

        init();

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        detail = getIntent().getIntExtra("detail", 0);
        id_movie = getIntent().getIntExtra("id_movie", 0);
        id_tv = getIntent().getIntExtra("id_tv", 0);

        if (detail == EXTRAS_DETAIL_MOVIE){
            getDataMovie();
        }else if (detail == EXTRAS_DETAIL_TV){
            getDataTv();
        }

        cekFavorite();
        
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void cekFavorite() {
        ContentValues args = new ContentValues();
        if (detail == EXTRAS_DETAIL_MOVIE){
            if (helper.cekFavorite(id_movie, "movie")){
                fb_favorit.setImageResource(R.drawable.ic_favorite_black_24dp);
                fb_favorit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getContentResolver().delete(Uri.parse(CONTENT_URI+"/movie/"+id_movie), null, null);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.membatalkan_fav), Toast.LENGTH_SHORT).show();
                        fb_favorit.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                });
            }else {
                fb_favorit.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                fb_favorit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        args.put(ID, movieModel.getId());
                        args.put(TITLE, movieModel.getTitle());
                        args.put(POSTER, movieModel.getPosterPath());
                        args.put(TYPE, "movie");
                        getContentResolver().insert(Uri.parse(CONTENT_URI+"/movie"), args);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.menambah_fav), Toast.LENGTH_SHORT).show();
                        fb_favorit.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    }
                });
            }
        }else if (detail == EXTRAS_DETAIL_TV){
            if (helper.cekFavorite(id_tv, "tv")){
                fb_favorit.setImageResource(R.drawable.ic_favorite_black_24dp);
                fb_favorit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getContentResolver().delete(Uri.parse(CONTENT_URI+"/tv/"+id_tv), null, null);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.membatalkan_fav), Toast.LENGTH_SHORT).show();
                        fb_favorit.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                });
            }else {
                fb_favorit.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                fb_favorit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        args.put(ID, tvShowModel.getId());
                        args.put(TITLE, tvShowModel.getName());
                        args.put(POSTER, tvShowModel.getPosterPath());
                        args.put(TYPE, "tv");
                        getContentResolver().insert(Uri.parse(CONTENT_URI+"/tv"), args);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.menambah_fav), Toast.LENGTH_SHORT).show();
                        fb_favorit.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    }
                });
            }
        }
    }

    public void getDataTv(){
        DetailTvViewModelFactory factory = new DetailTvViewModelFactory(id_tv);
        viewModelTv = ViewModelProviders.of(this, factory).get(DetailTvViewModel.class);
        if (viewModelTv.getDetailTv().hasObservers()) {
            viewModelTv.getDetailTv().removeObservers(this);
        }
        viewModelTv.getDetailTv().observe(this, new Observer<TvShow>() {
            @Override
            public void onChanged(TvShow tvShow) {
                if (viewModelTv.getDetailTv().getValue() != null) {
                    coordinatorLayout.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    tvShowModel = tvShow;
                    getDetailTv(tvShow);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.pesan_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDetailTv(TvShow tvShow) {
        collapsingToolbarLayout.setTitle(tvShow.getName());
        Glide.with(DetailActivity.this).load(BuildConfig.BASE_URL_IMG+tvShow.getPosterPath()).into(iv_poster);
        Glide.with(DetailActivity.this).load(BuildConfig.BASE_URL_IMG+tvShow.getBackdropPath()).into(iv_poster_background);
        tv_title.setText(tvShow.getName());
        tv_rating.setText(String.valueOf(tvShow.getRating()));
        tv_rilis.setVisibility(View.INVISIBLE);
        if(tvShow.getOverview().isEmpty()){
            tv_deskripsi.setText(getString(R.string.description_empty));
        }else {
            tv_deskripsi.setText(tvShow.getOverview());
        }
    }

    public void getDataMovie(){
        DetailMovieViewModelFactory factory = new DetailMovieViewModelFactory(id_movie);
        viewModelMovie = ViewModelProviders.of(this, factory).get(DetailMovieViewModel.class);
        if (viewModelMovie.getDetailMovie().hasObservers()) {
            viewModelMovie.getDetailMovie().removeObservers(this);
        }
        viewModelMovie.getDetailMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (viewModelMovie.getDetailMovie().getValue() != null) {
                    coordinatorLayout.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    movieModel = movie;
                    getDetailMovie(movie);
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.pesan_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDetailMovie(Movie movie) {
        collapsingToolbarLayout.setTitle(movie.getTitle());
        Glide.with(DetailActivity.this).load(BuildConfig.BASE_URL_IMG+movie.getPosterPath()).into(iv_poster);
        Glide.with(DetailActivity.this).load(BuildConfig.BASE_URL_IMG+movie.getBackdropPath()).into(iv_poster_background);
        tv_title.setText(movie.getTitle());
        tv_rating.setText(String.valueOf(movie.getVoteAvarage()));
        tv_rilis.setText(movie.getReleaseDate());
        if(movie.getOverview().isEmpty()){
            tv_deskripsi.setText(getString(R.string.description_empty));
        }else {
            tv_deskripsi.setText(movie.getOverview());
        }
    }

    private void init(){
        iv_poster = findViewById(R.id.iv_poster);
        iv_poster_background = findViewById(R.id.iv_poster_background);
        iv_back = findViewById(R.id.iv_back);
        tv_rating = findViewById(R.id.tv_rating);
        tv_rilis = findViewById(R.id.tv_rilis);
        tv_title = findViewById(R.id.tv_title);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);
        fb_favorit = findViewById(R.id.fb_favorite);
        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
    }
}
