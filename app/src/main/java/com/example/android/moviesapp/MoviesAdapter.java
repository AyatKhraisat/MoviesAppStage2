package com.example.android.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviesapp.Data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private ArrayList<Movie> mMoviesList;
    private Context mcontext;
    private MoviesAdapterOnClickHandler mClickHandler;
    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    public interface MoviesAdapterOnClickHandler {
        public void onClick(int position);
    }

    public MoviesAdapter(Context context, MoviesAdapterOnClickHandler clickHandler) {
        mcontext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(imageView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mcontext).load(BASE_POSTER_URL + mMoviesList.get(position).getPosterPath()).into(holder.imageView);
        holder.imageView.setFocusable(true);  }

    @Override
    public int getItemCount() {
        if (null == mMoviesList) return 0;
        return mMoviesList.size();
    }

    public void setMoviesData(ArrayList<Movie> weatherData) {
        mMoviesList = weatherData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public ViewHolder(ImageView itemView) {
            super(itemView);
            imageView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(position);
        }
    }

}
