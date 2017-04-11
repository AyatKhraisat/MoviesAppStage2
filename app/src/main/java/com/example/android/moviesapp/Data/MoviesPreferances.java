package com.example.android.moviesapp.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.android.moviesapp.R;

/**
 * Created by dell on 02/04/2017.
 */

public class MoviesPreferances {

    public static String getPreferredMovieType(Context context) {
       SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForSort = context.getString(R.string.pref_movies_sort_type_key);
        String defaultSortType = context.getString(R.string.pref_movies_label_most_popular);
        return prefs.getString(keyForSort, defaultSortType);
    }
}
