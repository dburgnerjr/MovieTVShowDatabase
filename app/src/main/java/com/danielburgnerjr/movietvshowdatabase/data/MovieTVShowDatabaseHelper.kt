package com.danielburgnerjr.movietvshowdatabase.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MovieTVShowDatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieTVShowDatabaseContract.MovieEntry.TABLE_NAME + " (" +
                //MovieTVShowDatabaseContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_ID + " TEXT NOT NULL PRIMARY KEY, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_ORIGINALTITLE + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_OVERVIEW + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_POSTERPATH + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_BACKDROP + " TEXT, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_RELEASEDATE + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");"

        db.execSQL(SQL_CREATE_MOVIE_TABLE)

        val SQL_CREATE_TV_TABLE = "CREATE TABLE " +
                MovieTVShowDatabaseContract.TVEntry.TABLE_NAME + " (" +
                //MovieTVShowDatabaseContract.TVEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_ID + " TEXT NOT NULL PRIMARY KEY, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_ORIGINALTITLE + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_OVERVIEW + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_POSTERPATH + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_BACKDROP + " TEXT, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_RELEASEDATE + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_VOTEAVERAGE + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");"

        db.execSQL(SQL_CREATE_TV_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(" DROP TABLE IF EXISTS " + MovieTVShowDatabaseContract.MovieEntry.TABLE_NAME)
        db.execSQL(" DROP TABLE IF EXISTS " + MovieTVShowDatabaseContract.TVEntry.TABLE_NAME)
        onCreate(db)

    }

    companion object {
        private val DATABASE_NAME = "movietvshowdatabase.db"
        private val DATABASE_VERSION = 1
    }

}
