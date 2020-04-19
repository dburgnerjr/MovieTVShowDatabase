package com.danielburgnerjr.movietvshowdatabase

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

import butterknife.ButterKnife
import java.util.ArrayList

import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response

import com.danielburgnerjr.movietvshowdatabase.adapter.ReviewAdapter
import com.danielburgnerjr.movietvshowdatabase.adapter.VideoAdapter

import com.danielburgnerjr.movietvshowdatabase.api.MovieTVAPI

import com.danielburgnerjr.movietvshowdatabase.model.Movie
import com.danielburgnerjr.movietvshowdatabase.model.TV
import com.danielburgnerjr.movietvshowdatabase.model.Video
import com.danielburgnerjr.movietvshowdatabase.model.Review

import com.danielburgnerjr.movietvshowdatabase.data.MovieTVShowDatabaseContract
import com.danielburgnerjr.movietvshowdatabase.data.MovieTVShowDatabaseHelper

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

import com.squareup.picasso.Picasso

/**
 * Created by dburgnerjr on 6/5/17.
 */

class MovieDetailActivity : AppCompatActivity(), VideoAdapter.Callbacks, ReviewAdapter.Callbacks {

    private var mMovie: Movie? = null
    private var tTV: TV? = null
    private var mVideoAdapter: VideoAdapter? = null
    private var mReviewAdapter: ReviewAdapter? = null
    private var mDb: SQLiteDatabase? = null

    private var ivBackdrop: ImageView? = null
    private var ivPoster: ImageView? = null
    private var tvDescription: TextView? = null
    private var tvReleaseDateHeading: TextView? = null
    private var tvReleaseDate: TextView? = null
    private var rbRating: RatingBar? = null
    private var tvVideosHeading: TextView? = null
    private var rvVideoList: RecyclerView? = null
    private var tvReviewsHeading: TextView? = null
    private var rvReviews: RecyclerView? = null

    private var mFavoriteButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)

        var strDescription: String? = null
        var strReleaseDate: String? = null
        var dUserRating = 0.0
        var strPoster: String? = null
        var strBackdrop: String? = null

        ivBackdrop = findViewById<View>(R.id.ivBackdrop) as ImageView
        ivPoster = findViewById<View>(R.id.movie_poster) as ImageView
        tvDescription = findViewById<View>(R.id.movie_description) as TextView
        tvReleaseDateHeading = findViewById<View>(R.id.release_date_heading) as TextView
        tvReleaseDate = findViewById<View>(R.id.release_date) as TextView
        rbRating = findViewById<View>(R.id.rating) as RatingBar
        tvVideosHeading = findViewById<View>(R.id.videos_heading) as TextView
        rvVideoList = findViewById(R.id.video_list)
        tvReviewsHeading = findViewById<View>(R.id.reviews_heading) as TextView
        rvReviews = findViewById(R.id.reviews)
        mFavoriteButton = findViewById(R.id.favorite_button)
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val intent = intent
        if (intent == null) {
            closeOnError("Intent is null")
        }

        val data = getIntent().extras
        if (data == null) {
            closeOnError(getString(R.string.Data_Not_Found_Movie))
            return
        }

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = data.getParcelable(EXTRA_MOVIE)
            if (mMovie == null) {
                closeOnError(getString(R.string.Data_Not_Found_Movie))
                return
            }
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            tTV = data.getParcelable(EXTRA_TV)
            if (tTV == null) {
                closeOnError(getString(R.string.Data_Not_Found_TV))
                return
            }
        } else {
            throw IllegalArgumentException("Detail activity must receive a movie or TV parcelable")
        }

        val tbToolbar = findViewById<View>(R.id.tbToolbar) as Toolbar
        setSupportActionBar(tbToolbar)
        val ctlToolbarLayout = findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            ctlToolbarLayout.title = mMovie!!.title
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            ctlToolbarLayout.title = tTV!!.title
        }

        ctlToolbarLayout.setExpandedTitleColor(Color.WHITE)
        ctlToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)

        val llMovieLayout = LinearLayout(applicationContext)
        llMovieLayout.orientation = LinearLayout.HORIZONTAL

        ButterKnife.bind(this)

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            strDescription = mMovie!!.description
            tvReleaseDateHeading?.text = getString(R.string.release_date)
            strReleaseDate = mMovie!!.releaseDate
            dUserRating = mMovie!!.userRating
            strPoster = mMovie!!.poster
            strBackdrop = mMovie!!.backdrop

            if (!mMovie!!.isFavorite) {
                mFavoriteButton!!.setText(R.string.favorite)
            } else {
                mFavoriteButton!!.setText(R.string.unfavorite)
            }
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            strDescription = tTV!!.description
            tvReleaseDateHeading?.text = getString(R.string.first_air_date)
            strReleaseDate = tTV!!.releaseDate
            dUserRating = tTV!!.userRating
            strPoster = tTV!!.poster
            strBackdrop = tTV!!.backdrop
            if (!tTV!!.isFavorite) {
                mFavoriteButton!!.setText(R.string.favorite)
            } else {
                mFavoriteButton!!.setText(R.string.unfavorite)
            }

        }

        tvDescription?.text = strDescription
        tvReleaseDate?.text = strReleaseDate
        rbRating?.rating = dUserRating.toFloat()

        // For horizontal list of trailers

        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rvVideoList!!.layoutManager = layoutManager
        mVideoAdapter = VideoAdapter(ArrayList(), this)
        rvVideoList!!.adapter = mVideoAdapter
        rvVideoList!!.isNestedScrollingEnabled = false

        // Fetch trailers only if savedInstanceState == null
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_VIDEOS)) {
            val videos = savedInstanceState.getParcelableArrayList<Video>(EXTRA_VIDEOS)
            mVideoAdapter!!.addVideo(videos)
        } else {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                fetchTrailers(mMovie!!.id?.let { java.lang.Long.parseLong(it) })
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                fetchTrailers(tTV!!.id?.let { java.lang.Long.parseLong(it) })
            }
        }

        // For vertical list of reviews
        val llmReviews = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvReviews!!.layoutManager = llmReviews
        mReviewAdapter = ReviewAdapter(ArrayList(), this as ReviewAdapter.Callbacks)
        rvReviews!!.adapter = mReviewAdapter

        // Fetch reviews only if savedInstanceState == null
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_REVIEWS)) {
            val reviews = savedInstanceState.getParcelableArrayList<Review>(EXTRA_REVIEWS)
            mReviewAdapter!!.add(reviews)
        } else {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                fetchReviews(mMovie!!.id?.let { java.lang.Long.parseLong(it) })
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                fetchReviews(tTV!!.id?.let { java.lang.Long.parseLong(it) })
            }
        }

        val pmDbHelper = MovieTVShowDatabaseHelper(this)
        mDb = pmDbHelper.writableDatabase

        mFavoriteButton!!.setOnClickListener {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                if (mMovie!!.isFavorite) {
                    mMovie!!.isFavorite = false
                    mFavoriteButton!!.setText(R.string.favorite)
                    if (removeFromFavorites() != 0)
                        Toast.makeText(this, "This movie was successfully removed from your favorites.", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this, "This movie was unsuccessfully removed from your favorites.", Toast.LENGTH_LONG).show()
                } else {
                    mMovie!!.isFavorite = true
                    mFavoriteButton!!.setText(R.string.unfavorite)
                    if (addToFavorites() != 0L)
                        Toast.makeText(this, "This movie was successfully added to your favorites.", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this, "This movie was unsuccessfully added to your favorites.", Toast.LENGTH_LONG).show()
                }
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                if (tTV!!.isFavorite) {
                    tTV!!.isFavorite = false
                    mFavoriteButton!!.setText(R.string.favorite)
                    if (removeFromFavorites() != 0)
                        Toast.makeText(this, "This TV show was successfully removed from your favorites.", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this, "This TV show was unsuccessfully removed from your favorites.", Toast.LENGTH_LONG).show()
                } else {
                    tTV!!.isFavorite = true
                    mFavoriteButton!!.setText(R.string.unfavorite)
                    if (addToFavorites() != 0L)
                        Toast.makeText(this, "This TV show was successfully added to your favorites.", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this, "This TV show was unsuccessfully added to your favorites.", Toast.LENGTH_LONG).show()
                }
            }
        }

        Picasso.get()
                .load(TMDB_IMAGE_PATH + strPoster!!)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(ivPoster)
        Picasso.get()
                .load(TMDB_IMAGE_PATH + strBackdrop!!)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(ivBackdrop)
    }


    private fun fetchTrailers(lMovieId: Long?) {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { request -> request.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create(MovieTVAPI::class.java)
        if (intent.hasExtra(EXTRA_MOVIE)) {
            mtaService.getMovieVideos(lMovieId, object : Callback<Video.VideoResult> {
                override fun success(videoResult: Video.VideoResult, response: Response) {
                    mVideoAdapter!!.setVideoList(videoResult.videoList)
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        } else if (intent.hasExtra(EXTRA_TV)) {
            mtaService.getTVVideos(lMovieId, object : Callback<Video.VideoResult> {
                override fun success(videoResult: Video.VideoResult, response: Response) {
                    mVideoAdapter!!.setVideoList(videoResult.videoList)
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        }
    }

    private fun fetchReviews(lMovieId: Long?) {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { request -> request.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create(MovieTVAPI::class.java)
        if (intent.hasExtra(EXTRA_MOVIE)) {
            mtaService.getMovieReviews(lMovieId, object : Callback<Review.ReviewResult> {
                override fun success(reviewResult: Review.ReviewResult, response: Response) {
                    mReviewAdapter!!.setReviews(reviewResult.reviews)
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        } else if (intent.hasExtra(EXTRA_TV)) {
            mtaService.getTVReviews(lMovieId, object : Callback<Review.ReviewResult> {
                override fun success(reviewResult: Review.ReviewResult, response: Response) {
                    mReviewAdapter!!.setReviews(reviewResult.reviews)
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        }
    }

    private fun closeOnError(msg: String) {
        finish()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun addToFavorites(): Long {
        var rowCount = 0L
        val cv = ContentValues()
        if (intent.hasExtra(EXTRA_MOVIE)) {
            cv.put(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_ID, mMovie!!.id)
            cv.put(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_ORIGINALTITLE, mMovie!!.title)
            cv.put(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_OVERVIEW, mMovie!!.description)
            cv.put(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_POSTERPATH, mMovie!!.poster)
            cv.put(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_BACKDROP, mMovie!!.backdrop)
            cv.put(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_RELEASEDATE, mMovie!!.releaseDate)
            cv.put(MovieTVShowDatabaseContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE, mMovie!!.userRating)

            rowCount = mDb!!.insert(MovieTVShowDatabaseContract.MovieEntry.TABLE_NAME, null, cv)
        } else if (intent.hasExtra(EXTRA_TV)) {
            cv.put(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_ID, tTV!!.id)
            cv.put(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_ORIGINALTITLE, tTV!!.title)
            cv.put(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_OVERVIEW, tTV!!.description)
            cv.put(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_POSTERPATH, tTV!!.poster)
            cv.put(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_BACKDROP, tTV!!.backdrop)
            cv.put(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_RELEASEDATE, tTV!!.releaseDate)
            cv.put(MovieTVShowDatabaseContract.TVEntry.COLUMN_NAME_VOTEAVERAGE, tTV!!.userRating)

            rowCount = mDb!!.insert(MovieTVShowDatabaseContract.TVEntry.TABLE_NAME, null, cv)
        }
        return rowCount
    }

    private fun removeFromFavorites(): Int {
        var rowCount = 0
        if (intent.hasExtra(EXTRA_MOVIE)) {
            var uri = MovieTVShowDatabaseContract.MovieEntry.CONTENT_URI
            uri = uri.buildUpon().appendPath(mMovie!!.id.toString()).build()
            rowCount = contentResolver.delete(uri, null, null)
        } else if (intent.hasExtra(EXTRA_TV)) {
            var uri = MovieTVShowDatabaseContract.TVEntry.CONTENT_URI
            uri = uri.buildUpon().appendPath(tTV!!.id.toString()).build()
            rowCount = contentResolver.delete(uri, null, null)
        }
        return rowCount
    }

    override fun watch(video: Video, position: Int) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.key)))
    }

    override fun read(review: Review, position: Int) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(review.url)))
    }

    companion object {
        const val EXTRA_MOVIE = "movie"
        const val EXTRA_TV = "tv"
        const val EXTRA_VIDEOS = "video"
        const val EXTRA_REVIEWS = "review"
        const val TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500"
    }
}
