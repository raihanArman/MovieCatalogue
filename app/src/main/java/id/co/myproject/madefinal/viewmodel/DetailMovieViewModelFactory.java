package id.co.myproject.madefinal.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final int movieId;

    public DetailMovieViewModelFactory(int movieId) {
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailMovieViewModel(movieId);
    }
}
