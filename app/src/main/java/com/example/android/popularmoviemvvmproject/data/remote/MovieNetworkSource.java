package com.example.android.popularmoviemvvmproject.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.popularmoviemvvmproject.BuildConfig;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.data.models.Review;
import com.example.android.popularmoviemvvmproject.data.models.Trailer;
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
    // mutable list which contains values from network source
    private final MutableLiveData<List<Movie>> mDownloadedMovieDetails;
    private final MutableLiveData<List<Review>> mReviewList;
    private final MutableLiveData<List<Trailer>> mTrailerList;
    // checks about loading status & helps in loading indicator
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieNetworkSource sInstance;

    private MovieNetworkSource() {
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
        mTrailerList = new MutableLiveData<>();
    }

    /**
     * Get the singleton for this class
     */
    public static MovieNetworkSource getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieNetworkSource();
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

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<Trailer>> getTrailerList() {
        return mTrailerList;
    }

    public void loadMovies(String filterType) {
        isLoading.postValue(true);
        Call<MovieResponse> movieResponse = webService.loadMovies(filterType, BuildConfig.MovieApiKey);
        movieResponse.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    // posting value to the live data
                    mDownloadedMovieDetails.postValue(response.body().getMovies());
                    isLoading.postValue(false);
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
                BuildConfig.MovieApiKey);

        movieResponse.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    mReviewList.postValue(response.body().getResults());
                }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("myTag", t.getMessage());
            }
        });
    }


    public void loadTrailer(int id) {

        Call<TrailerResponse> trailerResponse = webService.loadTrailers("" + id,
                BuildConfig.MovieApiKey);

        trailerResponse.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful())
                    mTrailerList.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e("myTag", t.getMessage());
            }
        });

    }
}
