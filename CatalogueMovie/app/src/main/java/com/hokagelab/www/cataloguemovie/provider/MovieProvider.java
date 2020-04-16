package com.hokagelab.www.cataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hokagelab.www.cataloguemovie.db.DatabaseContract;
import com.hokagelab.www.cataloguemovie.db.MovieHelper;

public class MovieProvider extends ContentProvider {
    private static final int MOV = 1;
    private static final int MOV_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        //com.hokagelab.www.cataloguemovie/movie
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_MOVIE, MOV);

        //content:com.hokagelab.www.cataloguemovie/movie/id
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_MOVIE+"/#", MOV_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case MOV:
                cursor = movieHelper.queryProvider();
                break;
            case MOV_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;

        switch (sUriMatcher.match(uri)){
            case MOV:
                added = movieHelper.insertProvider(contentValues);
                break;
                default:
                    added = 0;
                    break;
        }

        if (added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(DatabaseContract.CONTENT_URI + "/" +added);
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOV_ID:
                deleted =  movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOV_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }
}
