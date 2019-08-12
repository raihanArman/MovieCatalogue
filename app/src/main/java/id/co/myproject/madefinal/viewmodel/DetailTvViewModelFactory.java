package id.co.myproject.madefinal.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailTvViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final int tvId;

    public DetailTvViewModelFactory(int tvId){
        this.tvId = tvId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailTvViewModel(tvId);
    }
}
