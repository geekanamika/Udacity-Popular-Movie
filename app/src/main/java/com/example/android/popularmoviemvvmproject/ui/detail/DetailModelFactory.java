package com.example.android.popularmoviemvvmproject.ui.detail;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Anamika Tripathi on 11/10/18.
 */
public class DetailModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int movieId;
    private Application mApplication;

    public DetailModelFactory(Application application, int movieId) {
        this.movieId = movieId;
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mApplication, movieId);
    }
}
