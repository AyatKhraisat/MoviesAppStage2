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



    public static URL getUrl(Context context) {

        String url = MoviesPreferances.getPreferredWeatherLocation(context);
        if (url.equals(TOP_RATED_URL))
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

    public  static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
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
        } finally {
            urlConnection.disconnect();
        }
    }}
