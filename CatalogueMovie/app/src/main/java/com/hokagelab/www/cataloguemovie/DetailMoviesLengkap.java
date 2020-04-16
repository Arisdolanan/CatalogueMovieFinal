package com.hokagelab.www.cataloguemovie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hokagelab.www.cataloguemovie.api.ApiClient;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.db.MovieHelper;
import com.hokagelab.www.cataloguemovie.model.Genres;
import com.hokagelab.www.cataloguemovie.model.Movie;
import com.hokagelab.www.cataloguemovie.model.MovieDetail;
import com.hokagelab.www.cataloguemovie.model.ProductionCompanies;
import com.hokagelab.www.cataloguemovie.model.ResultDua;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class DetailMoviesLengkap extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "007c868395e80dc2e4a833416b24efa5";
    TextView tvTitle, tvRelease, tvOverview, tv_genre, tv_rating, tv_website, tv_durasi, tv_company;
    ImageView imageBackdrop;

    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        tvTitle = findViewById(R.id.tv_title_detail);
        tvRelease = findViewById(R.id.tv_releasedate_detail);
        tvOverview = findViewById(R.id.tv_overview_detail);
        imageBackdrop = findViewById(R.id.imgBackdrop_detail);
        tv_rating = findViewById(R.id.tv_rating);
        tv_website = findViewById(R.id.tv_website);
        tv_genre = findViewById(R.id.tv_genre);
        tv_durasi = findViewById(R.id.tv_durasi);
        tv_company = findViewById(R.id.tv_company);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(this);

//        getData();
        getDetail();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }



//    public void getData(){
//
////        tvTitle.setText(getIntent().getStringExtra("title"));
////        tvRelease.setText(getIntent().getStringExtra("release_date"));
////        tvOverview.setText(getIntent().getStringExtra("overview"));
////
////        String imgPath = "http://image.tmdb.org/t/p/w780"+getIntent().getStringExtra("backdrop");
////        Glide.with(this)
////                .load(imgPath)
////                .into(imageBackdrop);
////
//
//    }

    public void getDetail(){
        int ID_M = getIntent().getIntExtra("id_movie", 0);
//        Log.e(TAG, "Data apa ini ? "+ID_M);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(DetailMoviesLengkap.this);
        progressDialog.setMax(20);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<ResultDua> call = apiService.reposDetail(ID_M, API_KEY);

        Call<ResultDua> calls = apiService.reposDetail(ID_M, API_KEY);

        call.enqueue(new Callback<ResultDua>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResultDua> call, Response<ResultDua> response) {
                progressDialog.dismiss();
                int id =response.body().getId();
                Log.d(TAG, "Data Movie = "+id);

                MovieDetail movieDetail = response.body();

                String judul = movieDetail.getTitle();
                String date = movieDetail.getReleaseDate();
                String overview = movieDetail.getOverview();
                String backdrop = movieDetail.getBackdropPath();
                String average = Double.toString(movieDetail.getVoteAverage());
                String website = movieDetail.getHomepage();
                int durasi = movieDetail.getRuntime();


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                Date convertedDate;
                try { convertedDate = dateFormat.parse(date);
                    SimpleDateFormat format2 = new SimpleDateFormat("d MMMM yyyy");
                    tvRelease.setText(format2.format(convertedDate));
                } catch (ParseException ignored) {}

                int hours = durasi / 60;
                int minutes = durasi % 60;
//                Log.e(TAG, "data = "+durasi);
//                Log.e(TAG, "jam : "+hours+" minutes : "+ minutes);

                for (int i = 0; i < movieDetail.getGenres().size(); i++) {
                    Genres genre = movieDetail.getGenres().get(i);

                    if(i < movieDetail.getGenres().size() - 1) {
                        tv_genre.append(genre.getName()+ ", ");
                    }else{
                        tv_genre.append(genre.getName());
                    }
                }

                for (int i = 0; i < movieDetail.getProductionCompanies().size(); i++) {
                    ProductionCompanies produ = movieDetail.getProductionCompanies().get(i);

                    if(i < movieDetail.getGenres().size() - 1) {
                        tv_company.setText(produ.getName()+ ", ");
                    }else{
                        tv_company.setText(produ.getName());
                    }
                }

                tvTitle.setText(judul);
                tvOverview.setText(overview);
                tv_rating.setText(average);
                tv_durasi.setText(hours + "h"+minutes+"m");

                if (website == null){
                    tv_website.setText("-");
                }else{
                    tv_website.setText(website);
                }

                String imgPath = "http://image.tmdb.org/t/p/w780"+backdrop;
                Glide.with(DetailMoviesLengkap.this)
                        .load(imgPath)
                        .into(imageBackdrop);
                }

            @Override
            public void onFailure(Call<ResultDua> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(DetailMoviesLengkap.this, "koneksi error :(", Toast.LENGTH_SHORT).show();

            }
        });
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


                    MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
                    Call<ResultDua> call = apiService.reposDetail(movieid, API_KEY);

                    call.enqueue(new Callback<ResultDua>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<ResultDua> call, Response<ResultDua> response) {
//                int id =response.body().getId();
//                Log.e(TAG, "Data Movie = "+id);
                            MovieDetail movieDetail = response.body();

                            String judul = movieDetail.getTitle();
                            String date = movieDetail.getReleaseDate();
                            String overview = movieDetail.getOverview();
                            String backdrop = movieDetail.getBackdropPath();
                            String average = Double.toString(movieDetail.getVoteAverage());
                            String website = movieDetail.getHomepage();
                            int durasi = movieDetail.getRuntime();


                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                            Date convertedDate;
                            try { convertedDate = dateFormat.parse(date);
                                SimpleDateFormat format2 = new SimpleDateFormat("d MMMM yyyy");
                                tvRelease.setText(format2.format(convertedDate));
                            } catch (ParseException ignored) {}

                            int hours = durasi / 60;
                            int minutes = durasi % 60;
//                Log.e(TAG, "data = "+durasi);
//                Log.e(TAG, "jam : "+hours+" minutes : "+ minutes);

                            for (int i = 0; i < movieDetail.getGenres().size(); i++) {
                                Genres genre = movieDetail.getGenres().get(i);

                                if(i < movieDetail.getGenres().size() - 1) {
                                    tv_genre.append(genre.getName()+ ", ");
                                }else{
                                    tv_genre.append(genre.getName());
                                }
                            }

                            for (int i = 0; i < movieDetail.getProductionCompanies().size(); i++) {
                                ProductionCompanies produ = movieDetail.getProductionCompanies().get(i);

                                if(i < movieDetail.getGenres().size() - 1) {
                                    tv_company.setText(produ.getName()+ ", ");
                                }else{
                                    tv_company.setText(produ.getName());
                                }
                            }

                            tvTitle.setText(judul);
                            tvOverview.setText(overview);
                            tv_rating.setText(average);
                            tv_durasi.setText(hours + "h"+minutes+"m");

                            if (website == null){
                                tv_website.setText("-");
                            }else{
                                tv_website.setText(website);
                            }

                            String imgPath = "http://image.tmdb.org/t/p/w780"+backdrop;
                            Glide.with(DetailMoviesLengkap.this)
                                    .load(imgPath)
                                    .into(imageBackdrop);
                        }

                        @Override
                        public void onFailure(Call<ResultDua> call, Throwable t) {
                            Log.e(TAG, t.toString());
                            Toast.makeText(DetailMoviesLengkap.this, "koneksi error :(", Toast.LENGTH_SHORT).show();

                        }
                    });


                }

                break;
        }
    }
}
