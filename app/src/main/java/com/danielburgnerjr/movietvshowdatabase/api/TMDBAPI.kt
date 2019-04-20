package com.danielburgnerjr.movietvshowdatabase.api

/**
 * Created by dburgnerjr on 6/4/17.
 */
import retrofit.Callback
import retrofit.http.GET
import retrofit.http.Query

interface TMDBAPI {
    @GET("movie/popular")
    fun getPopularMovies(): Callback<TMDBMovieResponse>;
}