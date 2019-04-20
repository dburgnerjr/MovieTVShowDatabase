package com.danielburgnerjr.movietvshowdatabase.api

import com.google.gson.annotations.SerializedName

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
        @SerializedName("id") val strID: String,
        @SerializedName("title") val strTitle: String,
        @SerializedName("poster_path") val strPoster: String,
        @SerializedName("overview") val strDescription: String,
        @SerializedName("backdrop_path") val strBackdrop: String,
        @SerializedName("release_date") val strReleaseDate: String,
        @SerializedName("vote_average") val dUserRating: Double
        val isFavorite: Boolean
)
