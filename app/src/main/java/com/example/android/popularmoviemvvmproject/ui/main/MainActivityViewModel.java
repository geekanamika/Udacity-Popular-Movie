package com.example.android.popularmoviemvvmproject.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.utils.InjectorUtil;

import java.util.List;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = InjectorUtil.provideRepository(application.getApplicationContext());
        repository.getDownloadedMovieData().observeForever(new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d("myTag", "update recyclerView");
                if ((movies != null ? movies.size() : 0) > 0) {
                    for (Movie m :
                            movies) {
                        Log.d("myTag", m.getTitle());
                    }
                }
            }
        });
    }

    public void startFetchingData(String filterType) {
        repository.startFetchingData(filterType);
    }
}
