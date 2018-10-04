package com.example.android.popularmoviemvvmproject.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.example.android.popularmoviemvvmproject.data.models.Movie;

import java.util.List;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMoviesToDb(List<Movie> movieList);
}
