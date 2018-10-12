package com.example.android.popularmoviemvvmproject.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.popularmoviemvvmproject.utils.Constant;

/**
 * Created by Anamika Tripathi on 12/10/18.
 */
public class AppPreferenceHelper implements PrefHelper {

    private static final String CURRENT_SORT_CRITERIA = "sort-criteria";

    private final SharedPreferences mPrefs;

    public AppPreferenceHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrentSortCriteria() {
        return mPrefs.getString(CURRENT_SORT_CRITERIA, Constant.POPULAR_SORT);
    }

    @Override
    public void setCurrentSortCriteria(String sort) {
        mPrefs.edit().putString(CURRENT_SORT_CRITERIA, sort).apply();
    }
}
