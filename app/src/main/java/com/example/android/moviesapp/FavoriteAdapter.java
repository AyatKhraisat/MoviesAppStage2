package com.example.android.moviesapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviesapp.Data.Movie;
import com.example.android.moviesapp.Data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by dell on 07/04/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.AdapterViewHolder> {
    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";

    private final Context mContext;
    private Cursor mCursor;
    final private ForecastAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ForecastAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    @Override
    public FavoriteAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_favorite, parent, false);

        view.setFocusable(true);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.AdapterViewHolder holder, int position) {
       mCursor.moveToPosition(position);
        String imageRecourse = mCursor.getString(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_IMAGE_PATH));

        String title = mCursor.getString(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_TITLE));
        Picasso.with(mContext).load(BASE_POSTER_URL + imageRecourse).into(holder.posterImageView);
        holder.titleTextView.setText(title);
    }

    public FavoriteAdapter(@NonNull Context context, ForecastAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }


    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }


    class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView titleTextView;
        final ImageView posterImageView;

        AdapterViewHolder(View view) {
            super(view);

            posterImageView = (ImageView) view.findViewById(R.id.iv_favorite_movie_icon);
            titleTextView = (TextView) view.findViewById(R.id.tv_favorite_movie_title);

            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onClick handler registered with this adapter, passing that
         * date.
         *
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            String title = mCursor.getString(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_TITLE));
            String releaseDate = mCursor.getString(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN__RELEASE_DATE));
            String overview = mCursor.getString(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_OVERVIEW));
            double voteAvg = mCursor.getDouble(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_VOTE_AVG));
            String imagePath = mCursor.getString(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_IMAGE_PATH));
            int movieId = mCursor.getInt(mCursor.getColumnIndex(MoviesContract.FavoriteEntity.COLUMN_MOVIE_ID));
            Movie movie = new Movie(imagePath, title, releaseDate, overview, voteAvg, movieId);
            mClickHandler.onClick(movie);

        }
    }
}
