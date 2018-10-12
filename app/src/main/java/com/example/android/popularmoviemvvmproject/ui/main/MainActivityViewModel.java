package com.example.android.popularmoviemvvmproject.ui.main;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.utils.Constant;
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
        if(repository.getCurrentSortCriteria().equals(Constant.POPULAR_SORT) ||
                repository.getCurrentSortCriteria().equals(Constant.TOP_RATED_SORT))
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
