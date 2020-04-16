package com.hokagelab.www.cataloguemovie;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterFragment;
import com.hokagelab.www.cataloguemovie.api.ApiClient;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.db.MovieHelper;
import com.hokagelab.www.cataloguemovie.model.Movie;
import com.hokagelab.www.cataloguemovie.model.MovieResponse;
import com.hokagelab.www.cataloguemovie.model.Result;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.BACKDROP;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.DATE;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.ID_MOVIE;
import static com.hokagelab.www.cataloguemovie.db.DatabaseContract.MovieColumns.TITLE;

//By Aris Kurniawan

public class DetailMovies extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    TextView tvTitle, tvRelease, tvOverview;
    ImageView imageBackdrop;

    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.tv_title_detail);
        tvRelease = findViewById(R.id.tv_releasedate_detail);
        tvOverview = findViewById(R.id.tv_overview_detail);
        imageBackdrop = findViewById(R.id.imgBackdrop_detail);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(this);

        getData();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    private void getData(){
        String imgPath = "http://image.tmdb.org/t/p/w780"+getIntent().getStringExtra("backdrop");
        Glide.with(this)
                .load(imgPath)
                .into(imageBackdrop);

        tvTitle.setText(getIntent().getStringExtra("title"));
        tvRelease.setText(getIntent().getStringExtra("release_date"));
        tvOverview.setText(getIntent().getStringExtra("overview"));

        int EXTRA_ID = getIntent().getIntExtra("id_movie", 0);
        Log.e(TAG, "Data apa ini ? "+EXTRA_ID);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Movie newFavorite = new Movie();
                ContentValues initialValues = new ContentValues();

                int movieid = getIntent().getIntExtra("id_movie", 0);

                Cursor data = movieHelper.searchQuery(movieid);

                if  (data.getCount() > 0) {
                    Log.e(TAG,"data"+data);
                    Snackbar.make(v, "Maaf film sudah ditambahkan", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    int id_movie = getIntent().getIntExtra("id_movie", 0);

                    String description = getIntent().getStringExtra("overview");
                    String title = getIntent().getStringExtra("title");
                    String release = getIntent().getStringExtra("release_date");
                    String poster = getIntent().getStringExtra("poster");

                    newFavorite.setId(id_movie);
                    initialValues.put(ID_MOVIE, id_movie);


                    newFavorite.setTitle(title);
                    newFavorite.setDescription(description);
                    newFavorite.setDate(release);
                    newFavorite.setBackdrop(poster);

                    initialValues.put(ID_MOVIE, id_movie);
                    initialValues.put(TITLE, title);
                    initialValues.put(DESCRIPTION, description);
                    initialValues.put(DATE, release);
                    initialValues.put(BACKDROP, poster);


                    if (initialValues == null){
                        Snackbar.make(v, "Gagal Menambahkan film favorit", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else{
                        Snackbar.make(v, "Berhasil Menambahkan film favorit", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        getContentResolver().insert(CONTENT_URI, initialValues);
                    }
                }
                break;
        }
    }
}
