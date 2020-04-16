package com.hokagelab.www.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hokagelab.www.cataloguemovie.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.BACKDROP;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.COMPANY;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.DATE;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.GENRE;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.RATING;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.RUNTIME;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.TITLE;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.WEBSITE;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    public static final String TAG = "tag";
    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context){
        this.context  = context;
    }

    public void open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Movie> query() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));

                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor searchQuery(int movie){
        return database.rawQuery("SELECT * FROM "+DATABASE_TABLE+ " WHERE id_movie = '"+movie+"'", null);
    }


    public long insert(ContentValues initialValues){
        Log.d("TAG", "insert: success");
        return database.insert(DATABASE_TABLE, null, initialValues);

    }

    public int delete(int id){
        return database.delete(TABLE_MOVIE, _ID+ " = '"+id+"'", null);
    }

    //    ContentProvider
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}