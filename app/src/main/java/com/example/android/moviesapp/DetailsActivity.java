package com.example.android.moviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviesapp.Data.Movie;
import com.example.android.moviesapp.Data.MoviesContract;
import com.example.android.moviesapp.Data.Review;
import com.example.android.moviesapp.Data.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_DETAIL_LOADER = 1;

    public static final String[] PROJECTION = {
            MoviesContract.FavoriteEntity.COLUMN_MOVIE_ID
    };

    private ImageView mMoviePoster;

    private TextView mMovieTitle;

    private TextView mMovieRate;

    private TextView mMovieDate;

    private TextView mMovieOverview;

    private ImageView mFavariteIcon;

    private TextView mReviewTextView;

    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    private Movie mMovie;

    private String mRealsesDate;

    private static boolean cursorHasValidData = false;

    private ArrayList<Trailer> mTrailers;

    private ArrayList<Review> mReviews;

    private LinearLayout mTrailersLayout;

    private LinearLayout mReviewsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);

        mMovieDate = (TextView) findViewById(R.id.tv_movie_release_date);

        mMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);

        mMovieRate = (TextView) findViewById(R.id.tv_movie_rate);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_name);

        mFavariteIcon = (ImageView) findViewById(R.id.iv_favorite_icon);

        mTrailersLayout = (LinearLayout) findViewById(R.id.trailers_layout);

        mReviewsLayout = (LinearLayout) findViewById(R.id.reviews_layout);

        mReviewTextView = (TextView) findViewById(R.id.tv_movie_review_title);

        cursorHasValidData = false;
        Intent intent = getIntent();

        mMovie = intent.getParcelableExtra("Movies");

        Picasso.with(this).load(BASE_POSTER_URL + mMovie.getPosterPath()).into(mMoviePoster);

        mMovieTitle.setText(mMovie.getOriginalTitle());

        String[] date = mMovie.getReleaseDate().split("-");

        mRealsesDate = date[0];

        mMovieDate.setText(mRealsesDate);

        mMovieOverview.setText(mMovie.getOverview());

        mMovieRate.setText(mMovie.getVoteAverage() + "/10");

        mTrailers = intent.getParcelableArrayListExtra("Trailers");

        if (mTrailers != null) {
            addTrailers();
        } else {
            mTrailersLayout.setVisibility(View.GONE);
        }

        mReviews = intent.getParcelableArrayListExtra("Reviews");

        if (mReviews != null) {
            addReviews();
        } else {

            mReviewsLayout.setVisibility(View.GONE);

            mReviewTextView.setVisibility(View.GONE);
        }

        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);

    }

    public void favoriteClick(View view) {
        if (cursorHasValidData) {

            deleteMovieFromFavorites();

            mFavariteIcon.setImageResource(R.drawable.ic_star_border_black_24dp);

        } else {

            insertMovieIntoFavorites();
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle loaderArgs) {

        switch (loaderId) {

            case ID_DETAIL_LOADER:
                String[] stringArgs = {String.valueOf(mMovie.getmId())};

                return new CursorLoader(this,
                        MoviesContract.FavoriteEntity.CONTENT_URI,
                        PROJECTION, MoviesContract.FavoriteEntity.COLUMN_MOVIE_ID + "=?"
                        , stringArgs,
                        null);


            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {


            cursorHasValidData = true;
        }

        if (cursorHasValidData) {

            mFavariteIcon.setImageResource(R.drawable.ic_star_black_24dp);

            return;
        }


    }

    public void insertMovieIntoFavorites() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MoviesContract.FavoriteEntity.COLUMN__RELEASE_DATE, mRealsesDate);
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_IMAGE_PATH, mMovie.getPosterPath());
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_OVERVIEW, mMovie.getOverview());
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_TITLE, mMovie.getOriginalTitle());
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_MOVIE_ID, mMovie.getmId());
        contentValues.put(MoviesContract.FavoriteEntity.COLUMN_VOTE_AVG, mMovie.getVoteAverage());


        Uri uri = getContentResolver().insert(MoviesContract.FavoriteEntity.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void deleteMovieFromFavorites() {
        String stringArgs[] = {String.valueOf(mMovie.getmId())};

        getContentResolver().delete(MoviesContract.FavoriteEntity.CONTENT_URI, MoviesContract.FavoriteEntity.COLUMN_MOVIE_ID + "=?", stringArgs);

        cursorHasValidData = false;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void addTrailers() {


        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewGroup[] viewGroups = new ViewGroup[mTrailers.size()];

        for (int i = 0; i < mTrailers.size(); i++) {

            viewGroups[i] = (ViewGroup) vi.inflate(R.layout.list_item_trailers, null);
            final int position = i;
            viewGroups[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Trailer trailer = mTrailers.get(position);

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.getKey()));
                    Intent chooserIntent = Intent.createChooser(intent, "Choose Application to open Trailer:");

                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(chooserIntent,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe)
                        startActivity(chooserIntent);

                }
            });
            mTrailersLayout.addView(viewGroups[i], i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }

    }

    public void addReviews() {

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewGroup[] viewGroups = new ViewGroup[mReviews.size()];

        for (int i = 0; i < mReviews.size(); i++) {
            viewGroups[i] = (ViewGroup) vi.inflate(R.layout.list_item_reviews, null);
            final int position = i;
            TextView content = (TextView) viewGroups[i].findViewById(R.id.tv_review_content);
            TextView author = (TextView) viewGroups[i].findViewById(R.id.tv_review_author);
            content.setText(mReviews.get(i).getmContent());
            author.setText(mReviews.get(i).getmAuthor() + ":");

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Review review = mReviews.get(position);

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getmUrl()));

                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe)
                        startActivity(intent);

                }
            });
            mReviewsLayout.addView(viewGroups[i], i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            Intent intent = new Intent(DetailsActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_favorite) {
            Intent intent = new Intent(DetailsActivity.this, FavoriteActivity.class);
            startActivity(intent);
            return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
