package com.example.android.moviesapp.utilities;

import com.example.android.moviesapp.Data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class OpenMoviesJsonUtils {

    private static final String OWM_OVERVIEW = "overview";
    private static final String OWM_POSTER_PATH = "poster_path";
    private static final String OWM_ORGINAL_TITLE = "original_title";
    private static final String OWM_VOTE_AVERAGE = "vote_average";
    private static final String OWM_RELEASE_DATE = "release_date";

    public static ArrayList<Movie> getMovieContentValuesFromJson(String movieJson)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(movieJson);
        JSONArray results = jsonObject.getJSONArray("results");
        ArrayList<Movie> resultslist = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject c = results.getJSONObject(i);
            String overview = c.getString(OWM_OVERVIEW);
            String poster = c.getString(OWM_POSTER_PATH);
            String title = c.getString(OWM_ORGINAL_TITLE);
            double averageRate = c.getDouble(OWM_VOTE_AVERAGE);
            String releaseDate = c.getString(OWM_RELEASE_DATE);
            resultslist.add(new Movie(poster, title, releaseDate, overview, averageRate));

        }
        return resultslist;
    }
}
