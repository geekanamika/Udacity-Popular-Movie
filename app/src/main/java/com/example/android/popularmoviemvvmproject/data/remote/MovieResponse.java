
package com.example.android.popularmoviemvvmproject.data.remote;

import java.util.List;

import com.example.android.popularmoviemvvmproject.data.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {

    @SerializedName("results")
    @Expose
    private List<Movie> movies = null;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
