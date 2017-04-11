package com.example.android.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviesapp.Data.Movie;
import com.example.android.moviesapp.Data.MoviesContract;
import com.example.android.moviesapp.Data.MoviesPreferances;
import com.example.android.moviesapp.Data.Review;
import com.example.android.moviesapp.Data.Trailer;
import com.example.android.moviesapp.utilities.NetworkUtils;
import com.example.android.moviesapp.utilities.OpenMoviesJsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>, MoviesAdapter.MoviesAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView mRecyclerView;

    private MoviesAdapter mAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private static final int ID_NETWORK_LOADER = 0;


    private ArrayList<Movie> mMoviesList;


    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
    private static final int ID_FAVORITE_LOADER = 3;
    public static final String[] PROJECTION = {
            MoviesContract.FavoriteEntity.COLUMN_OVERVIEW,
            MoviesContract.FavoriteEntity.COLUMN_IMAGE_PATH,
            MoviesContract.FavoriteEntity.COLUMN_VOTE_AVG,
            MoviesContract.FavoriteEntity.COLUMN_MOVIE_ID,
            MoviesContract.FavoriteEntity.COLUMN__RELEASE_DATE,
            MoviesContract.FavoriteEntity.COLUMN_TITLE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager
                = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MoviesAdapter(MainActivity.this, this);

        mRecyclerView.setAdapter(mAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

    if(MoviesPreferances.isMostPopularPreferredMovieType(this)||MoviesPreferances.isTopRatedPreferredMovieType(this))
        getSupportLoaderManager().initLoader(ID_NETWORK_LOADER, null, this);

        else getSupportLoaderManager().initLoader(ID_FAVORITE_LOADER, null, this);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

        switch (id) {

            case ID_FAVORITE_LOADER:

                return new AsyncTaskLoader<ArrayList<Movie>>(MainActivity.this) {
                    ArrayList<Movie> mMoviesData = null;

                    @Override
                    protected void onStartLoading() {
                        if (mMoviesData != null) {
                            deliverResult(mMoviesData);
                        } else {
                            mLoadingIndicator.setVisibility(View.VISIBLE);
                            forceLoad();
                        }
                    }

                    @Override
                    public ArrayList<Movie> loadInBackground() {
                        ArrayList<Movie> mMoviesData = new ArrayList<>();
                        Cursor cursor = getContentResolver().query(MoviesContract.FavoriteEntity.CONTENT_URI,
                                PROJECTION, null, null, MoviesContract.FavoriteEntity._ID);
                        try {
                            while (cursor.moveToNext()) {
                                String title = cursor.getString(cursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_TITLE));
                                String releaseDate = cursor.getString(cursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN__RELEASE_DATE));
                                String overview = cursor.getString(cursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_OVERVIEW));
                                double voteAvg = cursor.getDouble(cursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_VOTE_AVG));
                                String imagePath = cursor.getString(cursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_IMAGE_PATH));
                                int movieId = cursor.getInt(cursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_MOVIE_ID));
                                Movie movie = new Movie(imagePath, title, releaseDate, overview, voteAvg, movieId);
                                mMoviesData.add(movie);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        } finally {
                            cursor.close();
                        }
                        return mMoviesData;

                    }

                    @Override
                    public void deliverResult(ArrayList<Movie> data) {
                        mMoviesData = data;
                        super.deliverResult(data);
                    }
                };


            case ID_NETWORK_LOADER:
                return new AsyncTaskLoader<ArrayList<Movie>>(MainActivity.this) {

                    ArrayList<Movie> mMoviesData = null;

                    @Override
                    protected void onStartLoading() {
                        if (mMoviesData != null) {
                            deliverResult(mMoviesData);
                        } else {
                            mLoadingIndicator.setVisibility(View.VISIBLE);
                            forceLoad();
                        }
                    }

                    @Override
                    public ArrayList<Movie> loadInBackground() {

                        URL MovieRequestUrl = NetworkUtils.getUrl(MainActivity.this);


                        try {
                            String jsonMoviesResponse = NetworkUtils
                                    .getResponseFromHttpUrl(MovieRequestUrl);

                            ArrayList<Movie> jsonMoviesData = OpenMoviesJsonUtils
                                    .getMovieContentValuesFromJson(jsonMoviesResponse);
                            for (int i = 0; i < jsonMoviesData.size(); i++) {
                                Movie movie = jsonMoviesData.get(i);
                                int movieId = movie.getmId();
                                URL trailerUrl = NetworkUtils.buildTrailerUrl(String.valueOf(movieId));
                                URL reviewUrl = NetworkUtils.buildReviewsUrl(String.valueOf(movieId));
                                String jsonTrailerResponse = NetworkUtils
                                        .getResponseFromHttpUrl(trailerUrl);
                                String jsonReviewResponse = NetworkUtils
                                        .getResponseFromHttpUrl(reviewUrl);
                                if (!TextUtils.isEmpty(jsonReviewResponse)) {
                                    ArrayList<Trailer> jsonTrailerData = OpenMoviesJsonUtils
                                            .getTrailerListFromJson(jsonTrailerResponse);
                                    jsonMoviesData.get(i).setTrailers(jsonTrailerData);
                                }
                                if (!TextUtils.isEmpty(jsonReviewResponse)) {
                                    ArrayList<Review> jsonReviewData = OpenMoviesJsonUtils
                                            .getReviewsListFromJson(jsonReviewResponse);
                                    jsonMoviesData.get(i).setmReviews(jsonReviewData);
                                }


                            }
                            return jsonMoviesData;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    public void deliverResult(ArrayList<Movie> data) {
                        mMoviesData = data;
                        super.deliverResult(data);
                    }
                };

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);

        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.setMoviesData(data);
        if (null == data) {
            showErrorMessage();
        } else {
            showDataView();
            mMoviesList = data;
        }
    }

    private void showDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        mMoviesList = null;
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        Movie movie = mMoviesList.get(position);
        intent.putExtra("Movies", movie);
        ArrayList<Trailer> trailers = movie.getTrailers();
        ArrayList<Review> reviews = movie.getmReviews();
        intent.putExtra("Reviews", reviews);
        intent.putExtra("Trailers", trailers);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();


        if (PREFERENCES_HAVE_BEEN_UPDATED) {

            if (MoviesPreferances.isTopRatedPreferredMovieType(MainActivity.this) ||
                    MoviesPreferances.isMostPopularPreferredMovieType(MainActivity.this))

                getSupportLoaderManager().restartLoader(ID_NETWORK_LOADER, null, this);
            else
                getSupportLoaderManager().restartLoader(ID_FAVORITE_LOADER, null, this);

            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }

}
