package com.example.android.popularmoviemvvmproject.utils;

import java.util.HashMap;

/**
 * Created by Anamika Tripathi on 5/10/18.
 */
public class Constant {
    private HashMap<Integer, String> genreList = new HashMap<>();
    public static final String POPULAR_SORT = "popular";
    public static final String TOP_RATED_SORT = "top_rated";
    public static final String FAVOURITE_SORT = "favourites";
    public static final String basePosterPathURL = "http://image.tmdb.org/t/p/w185/";
    public static final String YOUTUBE_BASE_URL_TRAILER = "https://www.youtube.com/watch?v=";
    public static final String YOUTUBE_BASE_URL_IMAGE = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_PACKAGE = "com.google.android.youtube";
    public static final String BUNDLE_MOVIE_ID = "movieId";

    public void createGenreList() {
        genreList.put(28, "Action");
        genreList.put(12, "Adventure");
        genreList.put(16, "Animation");
        genreList.put(35, "Comedy");
        genreList.put(80, "Crime");
        genreList.put(99, "Documentary");
        genreList.put(18, "Drama");
        genreList.put(10751, "Family");
        genreList.put(14, "Fantasy");
        genreList.put(36, "History");
        genreList.put(27, "Horror");
        genreList.put(10402, "Music");
        genreList.put(9648, "Mystery");
        genreList.put(10749, "Romance");
        genreList.put(878, "Science Fiction");
        genreList.put(10770, "TV Movie");
        genreList.put(53, "Thriller");
        genreList.put(10752, "Western");
        genreList.put(37, "Western");
    }

    public String getGenreList(int key) {
        return genreList.get(key);
    }
}
