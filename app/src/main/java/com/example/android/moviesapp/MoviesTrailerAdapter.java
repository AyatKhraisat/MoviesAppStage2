package com.example.android.moviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.moviesapp.Data.Trailer;

import java.util.ArrayList;

/**
 * Created by dell on 08/04/2017.
 */

public class MoviesTrailerAdapter extends ArrayAdapter<Trailer> {

    public MoviesTrailerAdapter(@NonNull Context context, @NonNull ArrayList<Trailer> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trailers, parent, false);

        notifyDataSetChanged();
    return   convertView;


    }

}
