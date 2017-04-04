package com.example.android.moviesapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviesapp.Data.Movie;
import com.example.android.moviesapp.Data.MoviesContract;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private ImageView mMoviePoster;
    private TextView mMovieTitle;
    private TextView mMovieRate;
    private TextView mMovieDate;
    private TextView mMovieOverview;
    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    private Movie mMovie;
    private String mRealsesDate;

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
        mMovie = movie;
        Picasso.with(this).load(BASE_POSTER_URL + movie.getPosterPath()).into(mMoviePoster);
        mMovieTitle.setText(movie.getOriginalTitle());
        String[] date = movie.getReleaseDate().split("-");
        mRealsesDate = date[0];
        mMovieDate.setText(mRealsesDate);
        mMovieOverview.setText(movie.getOverview());
        mMovieRate.setText(movie.getVoteAverage() + "/10");

    }

    public void favoriteClick(View view) {
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN__RELEASE_DATE, mRealsesDate);
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_IMAGE_PATH, mMovie.getPosterPath());
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_OVERVIEW, mMovie.getOverview());
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_TITLE, mMovie.getOriginalTitle());
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_VOTE_AVG, mMovie.getVoteAverage());


        Uri uri = getContentResolver().insert(MoviesContract.FavoriteEntity.CONTENT_URI, contentValues);

         if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

         finish();
    }
}
