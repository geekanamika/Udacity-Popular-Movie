package com.example.android.popularmoviemvvmproject.ui.detail;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.utils.InjectorUtil;

/**
 * Created by Anamika Tripathi on 11/10/18.
 */
public class DetailViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private LiveData<Integer> favouriteStatus;
    public DetailViewModel(Application application, int movieId) {
        movieRepository = InjectorUtil.provideRepository(application.getApplicationContext());
        favouriteStatus = movieRepository.checkIfMovieIsFavourite(movieId);
    }

    LiveData<Integer> getFavouriteStatus() {
        return favouriteStatus;
    }

    void setFavouriteMovie(Movie favourite) {
        movieRepository.insertFavouriteMovie(favourite);
    }

    void removeFavouriteMovie(int movieId) {
        movieRepository.removeFromFavourite(movieId);
    }
}
