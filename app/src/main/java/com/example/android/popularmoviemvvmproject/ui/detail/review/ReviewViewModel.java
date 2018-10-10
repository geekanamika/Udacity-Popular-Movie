package com.example.android.popularmoviemvvmproject.ui.detail.review;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.models.Review;
import com.example.android.popularmoviemvvmproject.utils.InjectorUtil;

import java.util.List;

/**
 * Created by Anamika Tripathi on 10/10/18.
 */
public class ReviewViewModel extends AndroidViewModel {
    private MovieRepository repository;
    private LiveData<List<Review>> reviewLiveData;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        repository = InjectorUtil.provideRepository(application.getApplicationContext());
        reviewLiveData = repository.getReviewsOfMovie();
    }

    public void startFetchingData(int movieId) {
        repository.startFetchingReviews(movieId);
    }

    public LiveData<List<Review>> getReviewResults() {
        return reviewLiveData;
    }

}
