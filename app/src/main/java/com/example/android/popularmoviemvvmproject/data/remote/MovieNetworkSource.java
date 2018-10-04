package com.example.android.popularmoviemvvmproject.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.data.models.Review;
import com.example.android.popularmoviemvvmproject.utils.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anamika Tripathi on 3/10/18.
 */
public class MovieNetworkSource {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private WebService webService;
    private final MutableLiveData<List<Movie>> mDownloadedMovieDetails;
    private final MutableLiveData<List<Review>> mReviewList;
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieNetworkSource sInstance;

    public MovieNetworkSource() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webService = retrofit.create(WebService.class);
        mDownloadedMovieDetails = new MutableLiveData<>();
        mReviewList = new MutableLiveData<>();
    }

    /**
     * Get the singleton for this class
     */
    public static MovieNetworkSource getInstance() {
        Log.d("myTag", "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieNetworkSource();
                Log.d("myTag", "Made new network data source");
            }
        }
        return sInstance;
    }


    public LiveData<List<Movie>> getDownloadedMovies() {
        return mDownloadedMovieDetails;
    }

    public LiveData<List<Review>> getReviewList() {
        return mReviewList;
    }



    public void loadMovies(String filterType) {

        Call<MovieResponse> movieResponse = webService.loadMovies(filterType,
                "a43f8ddec4a3456a6ad1029d2c9cac76");

        movieResponse.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    // posting value to the live data
                    mDownloadedMovieDetails.postValue(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("myTag", t.getMessage());
            }
        });

    }

    public void loadReviews(int id) {

        Call<ReviewResponse> movieResponse = webService.loadReviews("" + id,
                "a43f8ddec4a3456a6ad1029d2c9cac76");

        movieResponse.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful())
                    mReviewList.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }


    public void loadTrailer(int id) {

        Call<TrailerResponse> trailerResponse = webService.loadTrailers("" + id,
                "a43f8ddec4a3456a6ad1029d2c9cac76");

        trailerResponse.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful())
                    Log.d("myTag", response.body().getResults().get(0).getName());
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d("myTag", t.getMessage());
            }
        });

    }
}
