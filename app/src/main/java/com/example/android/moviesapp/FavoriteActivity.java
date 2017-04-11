package com.example.android.moviesapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviesapp.Data.Movie;
import com.example.android.moviesapp.Data.MoviesContract;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, FavoriteAdapter.ForecastAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;
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
        setContentView(R.layout.activity_favorite);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_favorite);
        mAdapter = new FavoriteAdapter(this, this);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */
        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(ID_FAVORITE_LOADER, null, FavoriteActivity.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle loaderArgs) {

        switch (loaderId) {

            case ID_FAVORITE_LOADER:
                Uri QueryUri = MoviesContract.FavoriteEntity.CONTENT_URI;

                return new CursorLoader(this,
                        QueryUri,
                        PROJECTION,
                        null,
                        null,
                        null);


            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.swapCursor(data);
////        if (null == data) {
//          //  showErrorMessage();
//        } else {
//           // showDataView();
//
//        }
    }

//    private void showDataView() {
//        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
//        mRecyclerView.setVisibility(View.VISIBLE);
//    }
//
//    private void showErrorMessage() {
//        mRecyclerView.setVisibility(View.INVISIBLE);
//        mErrorMessageDisplay.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(FavoriteActivity.this, DetailsActivity.class);
        intent.putExtra("Movies", movie);
        startActivity(intent);
    }
}
