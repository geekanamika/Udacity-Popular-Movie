package com.example.android.popularmoviemvvmproject.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.popularmoviemvvmproject.data.models.Movie;

/**
 * Created by Anamika Tripathi on 4/10/18.
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavDao favDao();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "MovieDb";
    private static volatile AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
