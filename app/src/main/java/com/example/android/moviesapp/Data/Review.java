package com.example.android.moviesapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 10/04/2017.
 */

public class Review implements Parcelable{

    private String mUrl;
    private String mContent;
    private String mAuthor;

    public Review(String mUrl, String mContent, String mAuthor) {
        this.mUrl = mUrl;
        this.mContent = mContent;
        this.mAuthor = mAuthor;
    }

    protected Review(Parcel in) {
        mUrl = in.readString();
        mContent = in.readString();
        mAuthor = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeString(mContent);
        dest.writeString(mAuthor);
    }
}
