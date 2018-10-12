package com.example.android.popularmoviemvvmproject.utils;

import android.content.Context;

import com.example.android.popularmoviemvvmproject.data.MovieRepository;
import com.example.android.popularmoviemvvmproject.data.local.AppDatabase;
import com.example.android.popularmoviemvvmproject.data.local.AppDbHelper;
import com.example.android.popularmoviemvvmproject.data.local.DbHelper;
import com.example.android.popularmoviemvvmproject.data.prefs.AppPreferenceHelper;
import com.example.android.popularmoviemvvmproject.data.remote.MovieNetworkSource;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
public class InjectorUtil {
    public static MovieRepository provideRepository(Context context) {
        // local db
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        AppDbHelper dbHelper = AppDbHelper.getInstance(database.favDao(), executors);
        // remote
        MovieNetworkSource networkDataSource =
                MovieNetworkSource.getInstance();
        // pref
        AppPreferenceHelper preferenceHelper = new AppPreferenceHelper(context, "movie-pref");

        return MovieRepository.getInstance(networkDataSource, preferenceHelper, dbHelper);
    }

}
