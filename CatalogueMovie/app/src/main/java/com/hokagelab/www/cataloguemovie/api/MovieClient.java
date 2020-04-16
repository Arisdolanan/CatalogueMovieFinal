package com.hokagelab.www.cataloguemovie.api;

import com.hokagelab.www.cataloguemovie.model.MovieResponse;
import com.hokagelab.www.cataloguemovie.model.ResultDua;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieClient {

    @GET("movie/popular")
    Call<MovieResponse> reposMovieList(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> reposSearch(@Query("api_key") String apiKey, @Query("query") String movies);

    @GET("movie/now_playing")
    Call<MovieResponse> reposNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> reposUpComing(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}?")
    Call<ResultDua> reposDetail(@Path("movie_id") int movies, @Query("api_key") String apiKey);
//    api.themoviedb.org/3/movie/252?api_key=007c868395e80dc2e4a833416b24efa5&language=en-US
}
