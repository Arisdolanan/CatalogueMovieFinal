package com.hokagelab.www.cataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "db_movie";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"+
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.ID_MOVIE,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.DESCRIPTION,
            DatabaseContract.MovieColumns.DATE,
            DatabaseContract.MovieColumns.BACKDROP
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +DatabaseContract.TABLE_MOVIE);
        onCreate(db);
    }
}
