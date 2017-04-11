package com.example.android.moviesapp.utilities;

import android.util.Log;

import com.example.android.moviesapp.Data.Movie;
import com.example.android.moviesapp.Data.Review;
import com.example.android.moviesapp.Data.Trailer;

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
    private static final String OWM_VIDEO_SITE = "site";
    private static final String OWM_VIDEO_KEY = "key";
    private static final String OWM_VIDEO_TYPE = "type";
    private static final String OWM_MOVIE_ID = "id";
    private static final String OWM_REVIEW_AUTHOR = "author";
    private static final String OWM_REVIEW_URL = "url";
    private static final String OWM_REVIEW_CONTENT = "content";
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
            int id=c.getInt(OWM_MOVIE_ID);
            resultslist.add(new Movie(poster, title, releaseDate, overview, averageRate,id));

        }
        return resultslist;
    }
    public static ArrayList<Trailer> getTreailerListFromJson(String movieJson)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(movieJson);
        JSONArray results = jsonObject.getJSONArray("results");
        ArrayList<Trailer> resultslist = new ArrayList<>();



        for (int i = 0; i < results.length(); i++) {
            try {
                JSONObject c = results.getJSONObject(i);
                String key = c.getString(OWM_VIDEO_KEY);
                String site = c.getString(OWM_VIDEO_SITE);
                String type = c.getString(OWM_VIDEO_TYPE);
                if(site.equalsIgnoreCase("YouTube")&&type.equalsIgnoreCase("Trailer"))
                resultslist.add(new Trailer(site, key, type));
            }
        catch (Exception e){
                Log.d("Trailer","Trailer parciing error");
            } }

        return resultslist;
    }
    public static ArrayList<Review> getReviewsListFromJson(String movieJson)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(movieJson);
        JSONArray results = jsonObject.getJSONArray("results");
        ArrayList<Review> resultslist = new ArrayList<>();



        for (int i = 0; i < results.length(); i++) {
            try {
                JSONObject c = results.getJSONObject(i);
                String author = c.getString(OWM_REVIEW_AUTHOR);
                String content = c.getString(OWM_REVIEW_CONTENT);
                String url = c.getString(OWM_REVIEW_URL);
                   resultslist.add(new  Review(url,content,author));
            }
            catch (Exception e){
                Log.d("Trailer","Trailer parciing error");
            } }

        return resultslist;
    }
}
