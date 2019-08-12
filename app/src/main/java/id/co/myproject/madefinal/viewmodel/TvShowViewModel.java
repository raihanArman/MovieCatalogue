package id.co.myproject.madefinal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import id.co.myproject.madefinal.model.TvShowResults;
import id.co.myproject.madefinal.repository.TvShowRepository;

public class TvShowViewModel extends AndroidViewModel {

    private LiveData<TvShowResults> mData;
    private TvShowRepository repository;

    public TvShowViewModel(@NonNull Application application) {
        super(application);
        repository = new TvShowRepository(application);
    }

    public LiveData<TvShowResults> mLiveDataTv(){
        mData = repository.getDataTv();
        return mData;
    }
}
