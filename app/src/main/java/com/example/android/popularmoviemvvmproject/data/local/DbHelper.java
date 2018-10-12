package com.example.android.popularmoviemvvmproject.data.local;

import android.arch.lifecycle.LiveData;

import com.example.android.popularmoviemvvmproject.data.models.Movie;

import java.util.List;

/**
 * Created by Anamika Tripathi on 12/10/18.
 */
public interface DbHelper {
    void insertFavouriteMovie(Movie fav);
    void removeFromFavourite(int id);
    LiveData<Integer> checkIfMovieIsFavourite(int id);
    LiveData<List<Movie>> getFavouriteMovieData();

}
