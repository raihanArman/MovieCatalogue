package id.co.myproject.madefinal.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import id.co.myproject.madefinal.BuildConfig;
import id.co.myproject.madefinal.util.NetworkCall;
import id.co.myproject.madefinal.model.TvShow;
import id.co.myproject.madefinal.model.TvShowResults;
import id.co.myproject.madefinal.util.Language;

public class TvShowRepository {
    public LiveData<TvShowResults> data;
    public LiveData<TvShow> dataDetailTv;

    public TvShowRepository(Application application){
        NetworkCall.fetchDataTv(BuildConfig.API_KEY, Language.getCountry(), 1);
    }

    public TvShowRepository(int id){
        NetworkCall.fetchDetailTv(id, BuildConfig.API_KEY, Language.getCountry());
    }

    public LiveData<TvShowResults> getDataTv(){
        data = NetworkCall.getDataTv();
        return data;
    }

    public LiveData<TvShow> getDetailTv(){
        dataDetailTv = NetworkCall.getDataDetailTv();
        return dataDetailTv;
    }
}
