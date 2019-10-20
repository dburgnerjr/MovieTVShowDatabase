package com.danielburgnerjr.movietvshowdatabase.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MovieTVShowDatabaseContentProvider : ContentProvider() {

    var movieDbHelper: MovieTVShowDatabaseHelper? = null

    override fun onCreate(): Boolean {
        movieDbHelper = MovieTVShowDatabaseHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        val db = movieDbHelper?.readableDatabase
        val match = sUriMatcher.match(uri)
        val retCursor: Cursor?

        when (match) {
            MOVIES -> retCursor = db?.query(MovieTVShowDatabaseContract.MovieEntry.TABLE_NAME, null, null, null, null, null,
                    MovieTVShowDatabaseContract.MovieEntry.COLUMN_TIMESTAMP)
            TVS -> retCursor = db?.query(MovieTVShowDatabaseContract.TVEntry.TABLE_NAME, null, null, null, null, null,
                    MovieTVShowDatabaseContract.TVEntry.COLUMN_TIMESTAMP)
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        retCursor?.setNotificationUri(context!!.contentResolver, uri)

        return retCursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = movieDbHelper?.writableDatabase
        val match = sUriMatcher.match(uri)
        val returnUri: Uri?
        val id: Long?
        when (match) {
            MOVIES -> {
                id = db?.insert(MovieTVShowDatabaseContract.MovieEntry.TABLE_NAME, null, values)
                if (id ?: 1 > 0) {
                    returnUri = ContentUris.withAppendedId(MovieTVShowDatabaseContract.MovieEntry.CONTENT_URI, id!!)
                } else {
                    throw android.database.SQLException("Failed to insert row into $uri")
                }
            }
            TVS -> {
                id = db?.insert(MovieTVShowDatabaseContract.TVEntry.TABLE_NAME, null, values)
                if (id ?: 1 > 0) {
                    returnUri = ContentUris.withAppendedId(MovieTVShowDatabaseContract.TVEntry.CONTENT_URI, id!!)
                } else {
                    throw android.database.SQLException("Failed to insert row into $uri")
                }
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return returnUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = movieDbHelper?.writableDatabase
        val match = sUriMatcher.match(uri)
        var moviesDeleted: Int = 0 // starts as 0
        var tvsDeleted: Int = 0 // starts as 0
        val id: String
        var deletedRecords: Int = 0

        when (match) {
            MOVIE_ID -> {
                id = uri.pathSegments[1]
                moviesDeleted = db!!.delete(MovieTVShowDatabaseContract.MovieEntry.TABLE_NAME, "id=?", arrayOf(id))
            }
            TV_ID -> {
                id = uri.pathSegments[1]
                tvsDeleted = db!!.delete(MovieTVShowDatabaseContract.TVEntry.TABLE_NAME, "id=?", arrayOf(id))
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        when (match) {
            MOVIE_ID -> {
                if (moviesDeleted != 0) {
                    context!!.contentResolver.notifyChange(uri, null)
                }
                deletedRecords = moviesDeleted
                if (tvsDeleted != 0) {
                    context!!.contentResolver.notifyChange(uri, null)
                }
                deletedRecords = tvsDeleted
            }
            TV_ID -> {
                if (tvsDeleted != 0) {
                    context!!.contentResolver.notifyChange(uri, null)
                }
                deletedRecords = tvsDeleted
            }
        }
        return deletedRecords
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    companion object {
        val MOVIES = 100
        val MOVIE_ID = 101
        val TVS = 200
        val TV_ID = 201
        private val sUriMatcher = buildUriMatcher()

        fun buildUriMatcher(): UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(MovieTVShowDatabaseContract.AUTHORITY, MovieTVShowDatabaseContract.MOVIE_ENTRY, MOVIES)
            uriMatcher.addURI(MovieTVShowDatabaseContract.AUTHORITY, MovieTVShowDatabaseContract.MOVIE_ENTRY + "/*", MOVIE_ID)

            uriMatcher.addURI(MovieTVShowDatabaseContract.AUTHORITY, MovieTVShowDatabaseContract.TV_ENTRY, TVS)
            uriMatcher.addURI(MovieTVShowDatabaseContract.AUTHORITY, MovieTVShowDatabaseContract.TV_ENTRY + "/*", TV_ID)
            return uriMatcher
        }
    }
}
