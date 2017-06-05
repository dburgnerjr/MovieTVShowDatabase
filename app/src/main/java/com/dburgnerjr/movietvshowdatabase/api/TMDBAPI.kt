package com.dburgnerjr.movietvshowdatabase.api

/**
 * Created by dburgnerjr on 6/4/17.
 */
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBAPI {
    @GET("movie/popular?api_key=8a6f42fe5f7efc6139cda365db5c89a1")
    fun getPopularMovies(): Call<TMDBMovieResponse>;
}