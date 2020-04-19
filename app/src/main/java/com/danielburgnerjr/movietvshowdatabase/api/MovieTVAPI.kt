package com.danielburgnerjr.movietvshowdatabase.api

import com.danielburgnerjr.movietvshowdatabase.model.Movie
import com.danielburgnerjr.movietvshowdatabase.model.TV
import com.danielburgnerjr.movietvshowdatabase.model.Video
import com.danielburgnerjr.movietvshowdatabase.model.Review

import retrofit.Callback
import retrofit.http.GET
import retrofit.http.Path

/**
 * Created by dburgnerjr on 6/5/17.
 */
interface MovieTVAPI {
    @GET("/movie/popular")
    fun getPopularMovies(cb: Callback<Movie.MovieResult>)

    @GET("/movie/top_rated")
    fun getTopRatedMovies(cb: Callback<Movie.MovieResult>)

    @GET("/movie/now_playing")
    fun getNowPlayingMovies(cb: Callback<Movie.MovieResult>)

    @GET("/movie/upcoming")
    fun getUpcomingMovies(cb: Callback<Movie.MovieResult>)

    @GET("/movie/{id}/videos")
    fun getMovieVideos(@Path("id") movieId: Long?, cb: Callback<Video.VideoResult>)

    @GET("/movie/{id}/reviews")
    fun getMovieReviews(@Path("id") movieId: Long?, cb: Callback<Review.ReviewResult>)

    @GET("/tv/popular")
    fun getPopularTVShows(cb: Callback<TV.TVResult>)

    @GET("/tv/top_rated")
    fun getTopRatedTVShows(cb: Callback<TV.TVResult>)

    @GET("/tv/{id}/videos")
    fun getTVVideos(@Path("id") tvId: Long?, cb: Callback<Video.VideoResult>)

    @GET("/tv/{id}/reviews")
    fun getTVReviews(@Path("id") tvId: Long?, cb: Callback<Review.ReviewResult>)
/*
    @GET("/search/movie")
    fun getSearchMovie(cb: Callback<Movie.MovieResult>)
*/

}
