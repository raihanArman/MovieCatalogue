package id.co.myproject.favoritecatalogue;

import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.co.myproject.favoritecatalogue.model.Movie;
import id.co.myproject.favoritecatalogue.model.TvShow;
import id.co.myproject.favoritecatalogue.request.ApiRequest;
import id.co.myproject.favoritecatalogue.request.RetrofitRequest;
import id.co.myproject.favoritecatalogue.util.Language;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView iv_poster, iv_poster_background, iv_back;
    private TextView tv_rilis, tv_rating, tv_deskripsi, tv_title;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressDialog progressDialog;
    private int detail;
    private int id_movie;
    private int id_tv;
    Movie movieModel;
    TvShow tvShowModel;
    ApiRequest apiRequest;

    public static final int EXTRAS_DETAIL_MOVIE = 1;
    public static final int EXTRAS_DETAIL_TV = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieModel = new Movie();
        tvShowModel = new TvShow();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cek");
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getDataTv() {
        apiRequest.getDetailTv(id_tv, BuildConfig.API_KEY, Language.getCountry())
                .enqueue(new Callback<TvShow>() {
                    @Override
                    public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                        progressDialog.dismiss();
                        TvShow tvShow = response.body();
                        getDetailTv(tvShow);
                    }

                    @Override
                    public void onFailure(Call<TvShow> call, Throwable t) {

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
        tv_deskripsi.setText(tvShow.getOverview());
    }

    private void getDataMovie() {
        apiRequest.getDetailMovie(id_movie, BuildConfig.API_KEY, Language.getCountry())
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        progressDialog.dismiss();
                        Movie movie = response.body();
                        getDetailMovie(movie);
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });
    }

    private void getDetailMovie(Movie movie) {
        collapsingToolbarLayout.setTitle(movie.getTitle());
        Glide.with(DetailActivity.this).load(BuildConfig.BASE_URL_IMG+movie.getPosterPath()).into(iv_poster);
        Glide.with(DetailActivity.this).load(BuildConfig.BASE_URL_IMG+movie.getBackdropPath()).into(iv_poster_background);
//                    Glide.with(DetailFilmActivity.this).load(BuildConfig.BASE_URL_IMG+movieModel.getPosterPath()).into(iv_film_card);
        tv_title.setText(movie.getTitle());
        tv_rating.setText(String.valueOf(movie.getVoteAvarage()));
        tv_rilis.setText(movie.getReleaseDate());
        tv_deskripsi.setText(movie.getOverview());
    }


    private void init(){
        iv_poster = findViewById(R.id.iv_poster);
        iv_poster_background = findViewById(R.id.iv_poster_background);
        iv_back = findViewById(R.id.iv_back);
//        iv_film_card = findViewById(R.id.iv_film_card);
        tv_rating = findViewById(R.id.tv_rating);
        tv_rilis = findViewById(R.id.tv_rilis);
        tv_title = findViewById(R.id.tv_title);
        tv_deskripsi = findViewById(R.id.tv_deskripsi);
        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
    }
}
