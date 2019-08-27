package id.co.myproject.madefinal.util;

import android.database.Observable;
import android.util.Log;

import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.request.ApiRequest;
import id.co.myproject.madefinal.request.RetrofitRequest;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.model.TvShowResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall {
    private static MutableLiveData<MovieResults> data = new MutableLiveData<>();
    private static MutableLiveData<TvShowResults> dataTv = new MutableLiveData<>();
    private static MutableLiveData<Movie> dataDetailMovie = new MutableLiveData<>();
    private static MutableLiveData<TvShow> dataDetailTv = new MutableLiveData<>();


    public static void fetchData(String api_key, String language, int page){
        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getPopularMovie(api_key, language, page)
                .enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                        if (response.isSuccessful()){
                            MovieResults movieResults = response.body();
                            data.setValue(movieResults);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {
                        data.setValue(null);
                    }
                });
    }

    public static void fetchDataTv(String api_key, String language, int page){
        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getPopularTv(api_key, language, page)
                .enqueue(new Callback<TvShowResults>() {
                    @Override
                    public void onResponse(Call<TvShowResults> call, Response<TvShowResults> response) {
                        if (response.isSuccessful()){
                            TvShowResults tvShowResults = response.body();
                            dataTv.setValue(tvShowResults);
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShowResults> call, Throwable t) {
                        dataTv.setValue(null);
                    }
                });
    }

    public static void fetchDetailMovie(int id, String api_key,String language){
        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getDetailMovie(id, api_key, language)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()){
                            dataDetailMovie.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        dataDetailMovie.setValue(null);
                    }
                });
    }

    public static void fetchDetailTv(int id, String api_key, String language){
        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getDetailtv(id, api_key, language)
                .enqueue(new Callback<TvShow>() {
                    @Override
                    public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                        if (response.isSuccessful()){
                            dataDetailTv.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShow> call, Throwable t) {
                        dataDetailTv.setValue(null);
                    }
                });
    }


    public static LiveData<MovieResults> getData(){
        return data;
    }
    public static LiveData<Movie> getDataDetailMovie(){
        return dataDetailMovie;
    }
    public static LiveData<TvShow> getDataDetailTv(){
        return dataDetailTv;
    }
    public static LiveData<TvShowResults> getDataTv(){
        return dataTv;
    }
}
