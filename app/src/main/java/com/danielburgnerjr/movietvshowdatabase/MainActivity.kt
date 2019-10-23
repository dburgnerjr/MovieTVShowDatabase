package com.danielburgnerjr.movietvshowdatabase

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

import butterknife.ButterKnife

import com.danielburgnerjr.movietvshowdatabase.adapter.MovieAdapter
import com.danielburgnerjr.movietvshowdatabase.adapter.TVAdapter

import com.danielburgnerjr.movietvshowdatabase.api.MovieTVAPI

import com.danielburgnerjr.movietvshowdatabase.model.Movie
import com.danielburgnerjr.movietvshowdatabase.model.TV

import com.danielburgnerjr.movietvshowdatabase.data.MovieTVShowDatabaseContract
import com.danielburgnerjr.movietvshowdatabase.data.MovieTVShowDatabaseHelper

import com.squareup.picasso.Picasso

import java.util.ArrayList

import retrofit.Callback
import retrofit.RequestInterceptor
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response

class MainActivity : AppCompatActivity() {
    internal var rvRecyclerView: RecyclerView? = null
    internal var spnMenuOptions: Spinner? = null
    private var mMovieAdapter: MovieAdapter? = null
    private var mTVAdapter: TVAdapter? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvRecyclerView = findViewById<View>(R.id.rvRecyclerView) as RecyclerView
        spnMenuOptions = findViewById<View>(R.id.spnMenuOptions) as Spinner

        val mtDbHelper = MovieTVShowDatabaseHelper(this)
        mDb = mtDbHelper.getWritableDatabase()

        ButterKnife.bind(this)
        rvRecyclerView?.setHasFixedSize(true)
        rvRecyclerView?.layoutManager = GridLayoutManager(this, 2)
        rvRecyclerView?.layoutManager!!.isMeasurementCacheEnabled = false
        mMovieAdapter = MovieAdapter(this)
        mTVAdapter = TVAdapter(this)
        rvRecyclerView?.adapter = mMovieAdapter
        getPopularMovies()

        val strOptions = resources.getStringArray(R.array.sort_options)

        val arAdapter = ArrayAdapter(this, R.layout.spinner_item, strOptions)

        spnMenuOptions?.adapter = arAdapter

        spnMenuOptions?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                when (position) {
                    0 -> {
                        rvRecyclerView?.adapter = mMovieAdapter
                        getPopularMovies()
                    }
                    1 -> {
                        rvRecyclerView?.adapter = mMovieAdapter
                        getTopRatedMovies()
                    }
                    2 -> {
                        rvRecyclerView?.adapter = mMovieAdapter
                        getNowPlayingMovies()
                    }
                    3 -> {
                        rvRecyclerView?.adapter = mMovieAdapter
                        getUpcomingMovies()
                    }
                    4 -> {
                        rvRecyclerView?.adapter = mMovieAdapter
                        getFavoriteMovies()
                    }
                    5 -> {
                        rvRecyclerView?.adapter = mTVAdapter
                        getPopularTVShows()
                    }
                    6 -> {
                        rvRecyclerView?.adapter = mTVAdapter
                        getTopRatedTVShows()
                    }
                    7 -> {
                        rvRecyclerView?.adapter = mTVAdapter
                        getFavoriteTVShows()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        }
    }

    private fun getPopularMovies() {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { rfRequest -> rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java)
        mtaService.getPopularMovies(object : Callback<Movie.MovieResult> {
            override fun success(movieResult: Movie.MovieResult, response: Response) {
                mMovieAdapter!!.setMovieList(movieResult.results)
            }

            override fun failure(error: RetrofitError) {
                error.printStackTrace()
            }
        })
    }

    private fun getTopRatedMovies() {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { rfRequest -> rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java)
        mtaService.getTopRatedMovies(object : Callback<Movie.MovieResult> {
            override fun success(movieResult: Movie.MovieResult, response: Response) {
                mMovieAdapter!!.setMovieList(movieResult.results)
            }

            override fun failure(error: RetrofitError) {
                error.printStackTrace()
            }
        })
    }

    private fun getNowPlayingMovies() {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { rfRequest -> rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java)
        mtaService.getNowPlayingMovies(object : Callback<Movie.MovieResult> {
            override fun success(movieResult: Movie.MovieResult, response: Response) {
                mMovieAdapter!!.setMovieList(movieResult.results)
            }

            override fun failure(error: RetrofitError) {
                error.printStackTrace()
            }
        })
    }

    private fun getUpcomingMovies() {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { rfRequest -> rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java)
        mtaService.getUpcomingMovies(object : Callback<Movie.MovieResult> {
            override fun success(movieResult: Movie.MovieResult, response: Response) {
                mMovieAdapter!!.setMovieList(movieResult.results)
            }

            override fun failure(error: RetrofitError) {
                error.printStackTrace()
            }
        })
    }

    private fun getFavoriteMovies() {
        val cursor = mDb!!.query(MovieTVShowDatabaseContract.MovieEntry.TABLE_NAME, null, null, null, null, null,
                MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE)

        //TODO Build the movie list from the stored Ids
        val result = ArrayList<Movie>()

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_ID))

                val movC = Movie(
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_ORIGINALTITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_POSTERPATH)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_BACKDROP)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_RELEASEDATE)),
                        cursor.getDouble(cursor.getColumnIndex(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE)),
                        true)
                println(movC.poster + " " + movC.backdrop)
                result.add(movC)
            }
        } finally {
            cursor.close()
        }
        mMovieAdapter!!.setMovieList(result)
    }

    private fun getPopularTVShows() {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { rfRequest -> rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java)
        mtaService.getPopularTVShows(object : Callback<TV.TVResult> {
            override fun success(tvResult: TV.TVResult, response: Response) {
                mTVAdapter!!.setTVList(tvResult.results)
            }

            override fun failure(error: RetrofitError) {
                error.printStackTrace()
            }
        })
    }

    private fun getTopRatedTVShows() {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { rfRequest -> rfRequest.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java)
        mtaService.getTopRatedTVShows(object : Callback<TV.TVResult> {
            override fun success(tvResult: TV.TVResult, response: Response) {
                mTVAdapter!!.setTVList(tvResult.results)
            }

            override fun failure(error: RetrofitError) {
                error.printStackTrace()
            }
        })
    }

    private fun getFavoriteTVShows() {
        val cursor = mDb!!.query(MovieTVShowDatabaseContract.TVEntry.TABLE_NAME, null, null, null, null, null,
                MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_VOTEAVERAGE)

        //TODO Build the TV list from the stored Ids
        val result = ArrayList<TV>()

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_ID))

                val tvC = TV(
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_ORIGINALTITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_POSTERPATH)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_BACKDROP)),
                        cursor.getString(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_RELEASEDATE)),
                        cursor.getDouble(cursor.getColumnIndex(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_VOTEAVERAGE)),
                        true)
                println(tvC.poster + " " + tvC.backdrop)
                result.add(tvC)
            }
        } finally {
            cursor.close()
        }
        mTVAdapter!!.setTVList(result)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = rvRecyclerView?.layoutManager as GridLayoutManager?
        outState.putInt(CURRENT_RECYCLER_VIEW_POSITION, layoutManager!!.findFirstVisibleItemPosition())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            val currentPosition = savedInstanceState.getInt(CURRENT_RECYCLER_VIEW_POSITION)
            rvRecyclerView?.scrollToPosition(currentPosition)
        }
    }

    companion object {
        private val CURRENT_RECYCLER_VIEW_POSITION = "CurrentRecyclerViewPosition"
    }
}