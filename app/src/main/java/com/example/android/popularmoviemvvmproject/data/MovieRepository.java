package com.example.android.popularmoviemvvmproject.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.popularmoviemvvmproject.data.local.DbHelper;
import com.example.android.popularmoviemvvmproject.data.local.FavDao;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.data.models.Review;
import com.example.android.popularmoviemvvmproject.data.models.Trailer;
import com.example.android.popularmoviemvvmproject.data.prefs.AppPreferenceHelper;
import com.example.android.popularmoviemvvmproject.data.prefs.PrefHelper;
import com.example.android.popularmoviemvvmproject.data.remote.MovieNetworkSource;
import com.example.android.popularmoviemvvmproject.utils.AppExecutors;
import com.example.android.popularmoviemvvmproject.utils.Constant;

import java.util.List;

/**
 * Created by Anamika Tripathi on 2/10/18.
 */
public class MovieRepository implements PrefHelper {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieRepository sInstance;
    private final MovieNetworkSource movieNetworkSource;
    private final PrefHelper preferenceHelper;
    private final DbHelper dbHelper;

    private MovieRepository(MovieNetworkSource networkSource,
                            PrefHelper preferenceHelper, DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        movieNetworkSource = networkSource;
        this.preferenceHelper = preferenceHelper;
    }

    public synchronized static MovieRepository getInstance(
            MovieNetworkSource weatherNetworkDataSource,
            PrefHelper preferenceHelper, DbHelper dbHelper) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieRepository(weatherNetworkDataSource,
                        preferenceHelper, dbHelper);
            }
        }
        return sInstance;
    }

    /**
     *
     * local db related queries
     */

    /**
     * insert starred movie in db
     *
     * @param fav: fav movie object
     */
    public void insertFavouriteMovie(Movie fav) {
        dbHelper.insertFavouriteMovie(fav);
    }

    /**
     * remove movie from fav table if unstarred
     *
     * @param id
     */
    public void removeFromFavourite(int id) {
        dbHelper.removeFromFavourite(id);
    }

    /**
     * checks if movie is already fav or not, uses callback
     *
     * @param id
     */
    public LiveData<Integer> checkIfMovieIsFavourite(int id) {
        return dbHelper.checkIfMovieIsFavourite(id);
    }

    /**
     * @return list of all favourites movies saved and it's details
     */
    public LiveData<List<Movie>> getFavouriteMovieData() {
        return dbHelper.getFavouriteMovieData();
    }

    /*
        network related operations
     */
    public LiveData<List<Movie>> getDownloadedMovieData() {
        return movieNetworkSource.getDownloadedMovies();
    }


    public LiveData<List<Review>> getReviewsOfMovie() {
        return movieNetworkSource.getReviewList();
    }

    public LiveData<List<Trailer>> getTrailerOfMovie() {
        return movieNetworkSource.getmTrailerList();
    }

    public void startFetchingData(String filterType) {
        movieNetworkSource.loadMovies(filterType);
    }

    public void startFetchingReviews(int movieId) {
        movieNetworkSource.loadReviews(movieId);
    }

    public void startFetchingTrailers(int movieId) {
        movieNetworkSource.loadTrailer(movieId);
    }

    public LiveData<Boolean> isLoadingData() {
        return movieNetworkSource.getIsLoading();
    }

    /**
     * Preference functions
     */
    @Override
    public String getCurrentSortCriteria() {
        return preferenceHelper.getCurrentSortCriteria();
    }

    @Override
    public void setCurrentSortCriteria(String sort) {
        preferenceHelper.setCurrentSortCriteria(sort);
    }
}
