package id.co.myproject.madefinal.viewmodel;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import id.co.myproject.madefinal.model.Movie;
import id.co.myproject.madefinal.repository.MovieRepository;

public class DetailMovieViewModel extends ViewModel {
    LiveData<Movie> mData;
    private MovieRepository mRespository;

    public DetailMovieViewModel(int id) {
        mRespository = new MovieRepository(id);
    }

    public LiveData<Movie> getDetailMovie(){
        mData = mRespository.mLiveDataDetailMovie();
        return mData;
    }
}
