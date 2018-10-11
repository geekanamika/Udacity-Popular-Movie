package com.example.android.popularmoviemvvmproject.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmoviemvvmproject.data.models.Movie;

import java.util.List;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
@Dao
public interface FavDao {

    @Query("SELECT * FROM movieFavourites")
    LiveData<List<Movie>> getFavouritesMovieList();

    @Query("SELECT COUNT(*) FROM movieFavourites")
    LiveData<Integer> getFavouritesMovieListSize();

    @Query("SELECT COUNT(id) FROM movieFavourites WHERE id = :movieId")
    LiveData<Integer> isFavourite(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavouriteMovie(Movie favouriteMovie);

    @Query("DELETE FROM movieFavourites WHERE id = :movieId")
    void deleteFavouriteMovie(int movieId);

}
