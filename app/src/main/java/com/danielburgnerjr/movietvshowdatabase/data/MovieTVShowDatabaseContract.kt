package com.danielburgnerjr.movietvshowdatabase.data

import android.provider.BaseColumns
import androidx.core.net.toUri

object MovieTVShowDatabaseContract {

    const val AUTHORITY = "com.danielburgnerjr.movietvshowdatabase"
    val BASE_CONTENT_URI = "content://$AUTHORITY".toUri()
    const val MOVIE_ENTRY = "movie"
    const val TV_ENTRY = "tv"

    /* Define table and content */
    class MovieEntry : BaseColumns {
        companion object {

            val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_ENTRY).build()!!

            const val TABLE_NAME = "favorite_movie"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_ORIGINALTITLE = "originalTitle"
            const val COLUMN_NAME_OVERVIEW = "overview"
            const val COLUMN_NAME_POSTERPATH = "posterPath"
            const val COLUMN_NAME_BACKDROP = "backdrop"
            const val COLUMN_NAME_RELEASEDATE = "releaseDate"
            const val COLUMN_NAME_VOTEAVERAGE = "voteAverage"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    class TVEntry : BaseColumns {
        companion object {

            val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TV_ENTRY).build()!!

            const val TABLE_NAME = "favorite_tv"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_ORIGINALTITLE = "originalTitle"
            const val COLUMN_NAME_OVERVIEW = "overview"
            const val COLUMN_NAME_POSTERPATH = "posterPath"
            const val COLUMN_NAME_BACKDROP = "backdrop"
            const val COLUMN_NAME_RELEASEDATE = "releaseDate"
            const val COLUMN_NAME_VOTEAVERAGE = "voteAverage"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

}
