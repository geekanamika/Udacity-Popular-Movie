package com.example.android.popularmoviemvvmproject.data.local;

import android.arch.lifecycle.LiveData;

import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.example.android.popularmoviemvvmproject.utils.AppExecutors;

import java.util.List;

/**
 * Created by Anamika Tripathi on 12/10/18.
 */
public class AppDbHelper implements DbHelper {

    private final FavDao favDao;
    private final AppExecutors mExecutors;
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppDbHelper sInstance;

    private AppDbHelper(FavDao dao,
                        AppExecutors executors) {
        favDao = dao;
        mExecutors = executors;
    }

    public synchronized static AppDbHelper getInstance(
            FavDao favDao,
            AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppDbHelper(favDao,
                        executors);
            }
        }
        return sInstance;
    }

    @Override
    public void insertFavouriteMovie(final Movie fav) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                favDao.insertFavouriteMovie(fav);
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void removeFromFavourite(final int id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                favDao.deleteFavouriteMovie(id);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public LiveData<Integer> checkIfMovieIsFavourite(int id) {
        return favDao.isFavourite(id);
    }

    @Override
    public LiveData<List<Movie>> getFavouriteMovieData() {
        return favDao.getFavouritesMovieList();
    }
}
