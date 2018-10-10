package com.example.android.popularmoviemvvmproject.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.popularmoviemvvmproject.data.local.FavDao;
import com.example.android.popularmoviemvvmproject.data.models.Favourites;
import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.data.models.Review;
import com.example.android.popularmoviemvvmproject.data.models.Trailer;
import com.example.android.popularmoviemvvmproject.data.remote.MovieNetworkSource;
import com.example.android.popularmoviemvvmproject.utils.AppExecutors;

import java.util.List;

/**
 * Created by Anamika Tripathi on 2/10/18.
 */
public class MovieRepository {

    private static final String LOG_TAG = MovieRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieRepository sInstance;
    private final FavDao favDao;
    private final MovieNetworkSource movieNetworkSource;
    private final AppExecutors mExecutors;

    private MovieRepository(FavDao dao,
                            MovieNetworkSource networkSource,
                            AppExecutors executors) {
        favDao = dao;
        movieNetworkSource = networkSource;
        mExecutors = executors;
    }

    public synchronized static MovieRepository getInstance(
            FavDao favDao, MovieNetworkSource weatherNetworkDataSource,
            AppExecutors executors) {
        Log.d("myTag", "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieRepository(favDao, weatherNetworkDataSource,
                        executors);
                Log.d("myTag", "Made new repository");
            }
        }
        return sInstance;
    }

    /**
     * insert starred movie in db
     *
     * @param fav: fav movie object
     */
    public void insertFavouriteMovie(final Favourites fav) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                favDao.insertFavouriteMovie(fav);
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    /**
     * remove movie from fav table if unstarred
     *
     * @param id
     */
    public void removeFromFavourite(final int id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                favDao.deleteFavouriteMovie(id);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    /**
     * checks if movie is already fav or not, uses callback
     *
     * @param id
     */
    public LiveData<Integer> checkIfMovieIsFavourite(final int id) {
        return favDao.isFavourite(id);
    }

    /**
     * @return list of all favourites movies saved and it's details
     */
    public LiveData<List<Favourites>> getFavouriteMovieData() {
        return favDao.getFavouritesMovieList();
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

}
