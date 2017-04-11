package com.example.android.moviesapp.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.android.moviesapp.R;

/**
 * Created by dell on 02/04/2017.
 */

public class MoviesPreferances {


    private static final String TOP_RATED = "top_rated";
    private static final String MOST_POPULAR = "popular";
    private static final String FAVORITE = "favorites";

    public static String getPreferredMovieType(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForSort = context.getString(R.string.pref_movies_sort_type_key);
        String defaultSortType = context.getString(R.string.pref_movies_label_most_popular);
        return prefs.getString(keyForSort, defaultSortType);
    }

    public static boolean isFavoritePreferredMovieType(Context context) {
        String currentSort = MoviesPreferances.getPreferredMovieType(context);
        if (currentSort.equals(FAVORITE))
            return true;
        return false;

    }
    public static boolean isMostPopularPreferredMovieType(Context context) {
        String currentSort = MoviesPreferances.getPreferredMovieType(context);
        if (currentSort.equals(MOST_POPULAR))
            return true;

        return false;


    }
    public static boolean isTopRatedPreferredMovieType(Context context) {
        String currentSort = MoviesPreferances.getPreferredMovieType(context);

        if (currentSort.equals(TOP_RATED))
            return true;

        return false;


    }
}
