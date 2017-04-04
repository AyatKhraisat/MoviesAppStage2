package com.example.android.moviesapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.moviesapp.Data.MoviesContract.FavoriteEntity;


/**
 * Created by dell on 03/04/2017.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";


    private static final int DATABASE_VERSION =1;


    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE =

                "CREATE TABLE " + FavoriteEntity.TABLE_NAME + " (" +


                        FavoriteEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+

                        FavoriteEntity.COLUMN__RELEASE_DATE+ " INTEGER NOT NULL, "+

                        FavoriteEntity.COLUMN_TITLE + " TEXT NOT NULL,"+

                        FavoriteEntity.COLUMN_VOTE_AVG   + " REAL NOT NULL, "+
                        FavoriteEntity.COLUMN_IMAGE_PATH   + " TEXT NOT NULL, "+

                        FavoriteEntity.COLUMN_OVERVIEW + " TEXT NOT NULL"+
                        ");";


        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
