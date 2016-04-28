package com.android.khaled.topmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khaled on 06/04/16.
 */
public class Movi implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String poster;
    private String release_date;
    private float rate;

    public Movi() {
        super();
    }

    private Movi(Parcel in) {
        super();
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.release_date = in.readString();
        this.rate = in.readFloat();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Movi [id=" + id +
               ", title=" + title +
               ", Overview=" + overview +
               ", poster=" + poster +
                ", release_date=" + release_date +
                ", Rate=" + rate + "]";
    }

    public static final Creator<Movi> CREATOR = new Creator<Movi>() {
        @Override
        public Movi createFromParcel(Parcel in) {
            return new Movi(in);
        }

        @Override
        public Movi[] newArray(int size) {
            return new Movi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(poster);
        dest.writeString(release_date);
        dest.writeFloat(rate);
    }
}
