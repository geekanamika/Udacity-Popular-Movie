package com.example.android.popularmoviemvvmproject.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Anamika Tripathi on 3/10/18.
 */
public interface WebService {

    @GET("movie/{filter_type}")
    Call<MovieResponse> loadMovies(@Path("filter_type") String filterBy,
                                   @Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> loadTrailers(@Path("id") String id,
                                       @Query("api_key") String api_key);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> loadReviews(@Path("id") String id,
                                     @Query("api_key") String api_key);
}
