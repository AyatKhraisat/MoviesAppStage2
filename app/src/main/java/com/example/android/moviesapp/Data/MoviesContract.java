package com.example.android.moviesapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dell on 03/04/2017.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.moviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITE_MOVIE = "favorite";


    public final static class FavoriteEntity implements BaseColumns {


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE_MOVIE)
                .build();

        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN__RELEASE_DATE = "release_date";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_IMAGE_PATH = "image";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_VOTE_AVG = "vote_avg";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static Uri buildBovieUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }

    }
}
