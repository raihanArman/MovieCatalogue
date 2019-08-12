package id.co.myproject.madefinal.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.repository.TvShowRepository;

public class DetailTvViewModel extends ViewModel {
    LiveData<TvShow> mData;
    private TvShowRepository mRepository;

    public DetailTvViewModel(int id){
        mRepository = new TvShowRepository(id);
    }

    public LiveData<TvShow> getDetailTv(){
        mData = mRepository.getDetailTv();
        return mData;
    }
}
