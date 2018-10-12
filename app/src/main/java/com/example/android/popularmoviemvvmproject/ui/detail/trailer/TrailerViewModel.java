package com.example.android.popularmoviemvvmproject.ui.detail.trailer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.models.Trailer;
import com.example.android.popularmoviemvvmproject.utils.InjectorUtil;

import java.util.List;

/**
 * Created by Anamika Tripathi on 10/10/18.
 */
public class TrailerViewModel extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<List<Trailer>> trailerLiveData;

    public TrailerViewModel(@NonNull Application application) {
        super(application);
        repository = InjectorUtil.provideRepository(application.getApplicationContext());
        trailerLiveData = repository.getTrailerOfMovie();
    }

    public void startFetchingData(int movieId) {
        repository.startFetchingTrailers(movieId);
    }

    public LiveData<List<Trailer>> getTrailerResults() {
        return trailerLiveData;
    }
}
