package id.co.myproject.favoritecatalogue.request;

import id.co.myproject.favoritecatalogue.model.Movie;
import id.co.myproject.favoritecatalogue.model.TvShow;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("movie/{movie_id}")
    Call<Movie> getDetailMovie(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );


    @GET("tv/{tv_id}")
    Call<TvShow> getDetailTv(
            @Path("tv_id") long tvId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
