package com.danielburgnerjr.movietvshowdatabase.data

import android.net.Uri
import android.provider.BaseColumns

object MovieTVShowDatabaseContract {

    val AUTHORITY = "com.danielburgnerjr.movietvshowdatabase"
    val BASE_CONTENT_URI = Uri.parse("content://$AUTHORITY")
    val MOVIE_ENTRY = "movie"
    val TV_ENTRY = "tv"

    /* Define table and content */
    class MovieEntry : BaseColumns {
        companion object {

            val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_ENTRY).build()

            val TABLE_NAME = "favorite_movie"
            val COLUMN_NAME_ID = "id"
            val COLUMN_NAME_ORIGINALTITLE = "originalTitle"
            val COLUMN_NAME_OVERVIEW = "overview"
            val COLUMN_NAME_POSTERPATH = "posterPath"
            val COLUMN_NAME_BACKDROP = "backdrop"
            val COLUMN_NAME_RELEASEDATE = "releaseDate"
            val COLUMN_NAME_VOTEAVERAGE = "voteAverage"
            val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    class TVEntry : BaseColumns {
        companion object {

            val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TV_ENTRY).build()

            val TABLE_NAME = "favorite_tv"
            val COLUMN_NAME_ID = "id"
            val COLUMN_NAME_ORIGINALTITLE = "originalTitle"
            val COLUMN_NAME_OVERVIEW = "overview"
            val COLUMN_NAME_POSTERPATH = "posterPath"
            val COLUMN_NAME_BACKDROP = "backdrop"
            val COLUMN_NAME_RELEASEDATE = "releaseDate"
            val COLUMN_NAME_VOTEAVERAGE = "voteAverage"
            val COLUMN_TIMESTAMP = "timestamp"
        }
    }

}
