package com.example.android.popularmoviemvvmproject.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.data.remote.MovieResponse;
import com.example.android.popularmoviemvvmproject.utils.InjectorUtil;

import java.util.List;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
public class MainActivityViewModel extends ViewModel {
    private MovieRepository repository;
    private LiveData<List<Movie>> movieLiveData;

    MainActivityViewModel(Application application) {
        repository = InjectorUtil.provideRepository(application.getApplicationContext());
        movieLiveData = repository.getDownloadedMovieData();
        startFetchingData();
    }

    public void startFetchingData() {
        repository.startFetchingData(repository.getCurrentSortCriteria());
    }

    public LiveData<List<Movie>> getMovieResults() {
        return movieLiveData;
    }

    public LiveData<Boolean> getLoadingStatus () {
        return repository.isLoadingData();
    }

    public String getCurrentSortCriteria(){
        return repository.getCurrentSortCriteria();
    }

    public void setCurrentSortCriteria(String criteria){
        repository.setCurrentSortCriteria(criteria);
    }

    public LiveData<List<Movie>> getFavouritesMovie() {
        return repository.getFavouriteMovieData();

    }


}
