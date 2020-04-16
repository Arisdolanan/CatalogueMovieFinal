package com.hokagelab.www.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.hokagelab.www.cataloguemovie.db.DatabaseContract;

import static android.provider.BaseColumns._ID;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String date;
    private String poster;
    private String backdrop;
    private int movie_id;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.poster);
        dest.writeString(this.backdrop);
        dest.writeInt(this.movie_id);
    }

    public Movie() {
    }

    public  Movie(Cursor cursor){
        this.id = DatabaseContract.getColumnInt(cursor, _ID);
        this.movie_id = DatabaseContract.getColumnInt(cursor, DatabaseContract.MovieColumns.ID_MOVIE);
        this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.description = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.DESCRIPTION);
        this.backdrop = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP);
        this.date = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.DATE);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.poster = in.readString();
        this.backdrop = in.readString();
        this.movie_id = in.readInt();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
