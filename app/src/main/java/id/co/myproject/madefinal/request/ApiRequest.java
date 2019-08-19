package id.co.myproject.madefinal.request;

import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.model.TvShowResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {


    @GET("discover/movie")
    Call<MovieResults> getReleaseMovie(
            @Query("api_key") String apiKey,
            @Query("primary_release.gte") String date,
            @Query("primary_release_date.lte") String date_,
            @Query("language") String language
    );

    @GET("movie/popular")
    Call<MovieResults> getPopularMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/popular")
    Call<TvShowResults> getPopularTv(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<Movie> getDetailMovie(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<TvShow> getDetailtv(
            @Path("tv_id") long tvId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<MovieResults> getSearchMovie(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @GET("search/tv")
    Call<TvShowResults> getSearchTv(
        @Query("api_key") String apiKey,
        @Query("query") String query
    );

}
