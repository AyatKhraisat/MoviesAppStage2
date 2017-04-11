package com.example.android.moviesapp.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.moviesapp.BuildConfig;
import com.example.android.moviesapp.Data.MoviesPreferances;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;


public class NetworkUtils {

    private static final String MOVIES_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String TOP_RATED_URL = "top_rated";
    private static final String MOST_POPULAR_URL = "popular";
    private static final String API_KEY_PARAM = "api_key";
    private static final String VIDEO_PARAM = "videos";
    private static final String REVIEW_PARAM = "reviews";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGES_PARAM = "page";


    public static URL getUrl(Context context) {


        if (MoviesPreferances.isTopRatedPreferredMovieType(context))
            return buildUrl(TOP_RATED_URL);
        else return buildUrl(MOST_POPULAR_URL);
    }


    private static URL buildUrl(String moviesType) {
        Uri moviesUri = Uri.parse(MOVIES_DB_BASE_URL).buildUpon().appendPath(moviesType)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .build();


        try {
            URL moviesUrl = new URL(moviesUri.toString());
            Log.v(TAG, "URL: " + moviesUrl);
            return moviesUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL buildTrailerUrl(String movieId) {
        Uri moviesUri = Uri.parse(MOVIES_DB_BASE_URL).buildUpon().appendPath(movieId).appendPath(VIDEO_PARAM)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .build();


        try {
            URL moviesUrl = new URL(moviesUri.toString());
            Log.v(TAG, "URL: " + moviesUrl);
            return moviesUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL buildReviewsUrl(String movieId) {
        Uri moviesUri = Uri.parse(MOVIES_DB_BASE_URL).buildUpon().appendPath(movieId).appendPath(REVIEW_PARAM)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .appendQueryParameter(PAGES_PARAM, "1")
                .build();


        try {
            URL moviesUrl = new URL(moviesUri.toString());
            Log.v(TAG, "URL: " + moviesUrl);
            return moviesUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        int responceKey = urlConnection.getResponseCode();
        if (responceKey == 200) {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } else {
            urlConnection.disconnect();
            return null;
        }
    }
}
