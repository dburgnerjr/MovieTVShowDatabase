package com.danielburgnerjr.movietvshowdatabase.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieTVShowDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movietvshowdatabase.db";
    private static final int DATABASE_VERSION = 1;

    public MovieTVShowDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getTABLE_NAME() + " (" +
                //MovieTVShowDatabaseContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_NAME_ID() + " TEXT NOT NULL PRIMARY KEY, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_NAME_ORIGINALTITLE() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_NAME_OVERVIEW() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_NAME_POSTERPATH() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_NAME_BACKDROP() + " TEXT, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_NAME_RELEASEDATE() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_NAME_VOTEAVERAGE() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.MovieEntry.Companion.getCOLUMN_TIMESTAMP() + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

        final String SQL_CREATE_TV_TABLE = "CREATE TABLE " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getTABLE_NAME() + " (" +
                //MovieTVShowDatabaseContract.TVEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_NAME_ID() + " TEXT NOT NULL PRIMARY KEY, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_NAME_ORIGINALTITLE() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_NAME_OVERVIEW() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_NAME_POSTERPATH() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_NAME_BACKDROP() + " TEXT, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_NAME_RELEASEDATE() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_NAME_VOTEAVERAGE() + " TEXT NOT NULL, " +
                MovieTVShowDatabaseContract.TVEntry.Companion.getCOLUMN_TIMESTAMP() + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";

        db.execSQL(SQL_CREATE_TV_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + MovieTVShowDatabaseContract.MovieEntry.Companion.getTABLE_NAME());
        db.execSQL(" DROP TABLE IF EXISTS " + MovieTVShowDatabaseContract.TVEntry.Companion.getTABLE_NAME());
        onCreate(db);

    }

}
