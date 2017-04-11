package com.example.android.moviesapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 08/04/2017.
 */

public class Trailer implements Parcelable {

    private String site;
    private String key;
    private String type;

    public Trailer(String site, String key, String type) {
        this.site = site;
        this.key = key;
        this.type = type;

    }

    protected Trailer(Parcel in) {
        site = in.readString();
        key = in.readString();
        type = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(site);
        dest.writeString(key);
        dest.writeString(type);
    }
}
