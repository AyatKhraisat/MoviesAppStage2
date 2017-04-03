package com.example.android.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviesapp.Data.Movie;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private ImageView mMoviePoster;
    private TextView mMovieTitle;
    private TextView mMovieRate;
    private TextView mMovieDate;
    private TextView mMovieOverview;
    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        mMovieDate = (TextView) findViewById(R.id.tv_movie_release_date);
        mMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);
        mMovieRate = (TextView) findViewById(R.id.tv_movie_rate);
        mMovieTitle = (TextView) findViewById(R.id.tv_movie_name);
        Intent i = getIntent();
        Movie movie = i.getParcelableExtra("Movies");
        Picasso.with(this).load(BASE_POSTER_URL + movie.getPosterPath()).into(mMoviePoster);
        mMovieTitle.setText(movie.getOriginalTitle());
        String[] date = movie.getReleaseDate().split("-");
        mMovieDate.setText(date[0]);
        mMovieOverview.setText(movie.getOverview());
        mMovieRate.setText(movie.getVoteAverage() + "/10");

    }
}
