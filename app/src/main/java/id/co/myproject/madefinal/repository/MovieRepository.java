package id.co.myproject.madefinal.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.util.NetworkCall;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.util.Language;

public class MovieRepository {
    private LiveData<MovieResults> data;
    private LiveData<Movie> dataDetailMovie;

    public MovieRepository(Application application){
        NetworkCall.fetchData(BuildConfig.API_KEY, Language.getCountry(), 1);
    }



    public MovieRepository(int id){
        NetworkCall.fetchDetailMovie(id, BuildConfig.API_KEY, Language.getCountry());
    }

    public LiveData<MovieResults> mLiveData(){
        data = NetworkCall.getData();
        return data;
    }

    public LiveData<Movie> mLiveDataDetailMovie(){
        dataDetailMovie = NetworkCall.getDataDetailMovie();
        return dataDetailMovie;
    }

}
