package com.example.android.popularmoviemvvmproject.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmoviemvvmproject.data.models.Favourites;

import java.util.List;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
@Dao
public interface FavDao {

    @Query("SELECT * FROM FAVOURITES")
    LiveData<List<Favourites>> getFavouritesMovieList();

    @Query("SELECT COUNT(id) FROM favourites WHERE id = :movieId")
    LiveData<Integer> isFavourite(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavouriteMovie(Favourites favouriteMovie);

    @Query("DELETE FROM favourites WHERE id = :movieId")
    void deleteFavouriteMovie(int movieId);

}
