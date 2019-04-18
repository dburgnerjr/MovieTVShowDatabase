package com.danielburgnerjr.movietvshowdatabase.commons

/**
 * Created by dburgnerjr on 6/4/17.
 */
import com.danielburgnerjr.movietvshowdatabase.commons.adapter.AdapterConstants
import com.danielburgnerjr.movietvshowdatabase.commons.adapter.ViewType

data class Movie(
        val strTitle: String,
        val strPoster: String,
        val strDescription: String,
        val strBackdrop: String
) : ViewType {
    override fun getViewType() = AdapterConstants.MOVIE
}
