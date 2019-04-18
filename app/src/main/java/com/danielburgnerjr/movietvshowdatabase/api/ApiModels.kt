package com.danielburgnerjr.movietvshowdatabase.api

/**
 * Created by dburgnerjr on 6/4/17.
 */
class TMDBMovieResponse(val data: TMDBDataResponse)

class TMDBDataResponse(
        val children: List<TMDBChildrenResponse>,
        val strAfter: String?,
        val strBefore: String?
)

class TMDBChildrenResponse(val data: TMDBMovieDataResponse)

class TMDBMovieDataResponse(
        val title: String,
        val poster: String,
        val description: String,
        val backdrop: String
)
