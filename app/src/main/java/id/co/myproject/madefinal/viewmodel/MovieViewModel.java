package id.co.myproject.madefinal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.model.MovieResults;
import id.co.myproject.madefinal.repository.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private LiveData<MovieResults> mData;
    private LiveData<Movie> mDataDetailMovie;

    private MovieRepository mRespository;

    public MovieViewModel(Application application) {
        super(application);
        mRespository = new MovieRepository(application);
    }
    public MovieViewModel(int id, Application application) {
        super(application);
        mRespository = new MovieRepository(id);
    }

    public LiveData<MovieResults> mLiveData(){
        mData = mRespository.mLiveData();
        return mData;
    }

    public LiveData<Movie> mLiveDataDetailMovie(){
        mDataDetailMovie = mRespository.mLiveDataDetailMovie();
        return mDataDetailMovie;
    }
}
