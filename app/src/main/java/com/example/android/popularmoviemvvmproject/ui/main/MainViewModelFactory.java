package com.example.android.popularmoviemvvmproject.ui.main;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.widget.ViewSwitcher;

import com.example.android.popularmoviemvvmproject.ui.detail.DetailViewModel;

/**
 * Created by Anamika Tripathi on 12/10/18.
 */
public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    //private String filterType;
    private Application mApplication;

    public MainViewModelFactory(Application application) {
        //this.filterType = filterType;
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(mApplication);
    }
}
