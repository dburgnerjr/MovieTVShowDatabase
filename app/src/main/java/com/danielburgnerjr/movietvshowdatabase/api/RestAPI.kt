package com.danielburgnerjr.movietvshowdatabase.api

/**
 * Created by dburgnerjr on 6/4/17.
 */
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI() {

    private val tmdbAPI: TMDBAPI

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        tmdbAPI = retrofit.create(TMDBAPI::class.java)
    }

    fun getMovie(): Call<TMDBMovieResponse> {
        return tmdbAPI.getPopularMovies()
    }
}