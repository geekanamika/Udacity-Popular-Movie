package com.example.android.popularmoviemvvmproject.utils;

import android.content.Context;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.local.AppDatabase;
import com.example.android.popularmoviemvvmproject.data.remote.MovieNetworkSource;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
public class InjectorUtil {
    public static MovieRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MovieNetworkSource networkDataSource =
                MovieNetworkSource.getInstance();
        return MovieRepository.getInstance(database.favDao(), networkDataSource, executors);
    }

}
