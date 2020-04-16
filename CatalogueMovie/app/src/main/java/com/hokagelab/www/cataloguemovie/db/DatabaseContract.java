package com.hokagelab.www.cataloguemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_MOVIE = "tb_movie";

    // Authority yang digunakan
    public static final String AUTHORITY = "com.hokagelab.www.cataloguemovie";
//    public static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns{
        //movie title
        public static String TITLE = "title";
        //movie description
        public static String DESCRIPTION = "description";
        //movie date
        public static String DATE = "date";

        public static String ID_MOVIE = "id_movie";

        public static String BACKDROP = "backdrop";

        public  static String GENRE = "genre";

        public  static String RUNTIME = "runtime";

        public static String RATING = "rating";

        public static String WEBSITE = "website";

        public static String COMPANY = "company";
    }


    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }


    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
