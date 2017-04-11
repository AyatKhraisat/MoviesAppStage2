package com.example.android.moviesapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    private String mPosterPath;
    private String mOriginalTitle;
    private String mReleaseDate;
    private String mOverview;
    private double mVoteAverage;
    private int mId;
    private ArrayList<Trailer> mTrailers;
    private ArrayList<Review> mReviews;
    public Movie(String poster, String title, String releaseDate, String overview, double voteAverage,int id) {
        this.mPosterPath = poster;
        this.mOriginalTitle = title;
        this.mOverview = overview;
        this.mVoteAverage = voteAverage;
        this.mReleaseDate = releaseDate;
        this.mId=id;

    }


    protected Movie(Parcel in) {
        mPosterPath = in.readString();
        mOriginalTitle = in.readString();
        mReleaseDate = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readDouble();
        mId = in.readInt();
        mTrailers = in.createTypedArrayList(Trailer.CREATOR);
        mReviews = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public ArrayList<Review> getmReviews() {
        return mReviews;
    }

    public void setmReviews(ArrayList<Review> mReviews) {
        this.mReviews = mReviews;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.mTrailers = trailers;
    }

    public ArrayList<Trailer> getTrailers() {
        return mTrailers;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public void setVoteAverage(double mvoteAverage) {
        this.mVoteAverage = mvoteAverage;
    }

    public int getmId() { return mId; }
    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPosterPath);
        dest.writeString(mOriginalTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mOverview);
        dest.writeDouble(mVoteAverage);
        dest.writeInt(mId);
        dest.writeTypedList(mTrailers);
        dest.writeTypedList(mReviews);
    }
}
