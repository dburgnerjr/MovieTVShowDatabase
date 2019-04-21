package com.danielburgnerjr.movietvshowdatabase

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

//import butterknife.ButterKnife
import java.util.ArrayList

import retrofit.Callback
import retrofit.RequestInterceptor
import retrofit.RestAdapter
import retrofit.RetrofitError
import retrofit.client.Response

import com.danielburgnerjr.movietvshowdatabase.commons.Movie
//import com.danielburgnerjr.movietvdb.data.MovieTVDBContract
//import com.danielburgnerjr.movietvdb.data.MovieTVDbHelper
import com.squareup.picasso.Picasso

/**
 * Created by dburgnerjr on 6/5/17.
 */

class MovieDetailActivity : AppCompatActivity()
        //, VideoAdapter.Callbacks, ReviewAdapter.Callbacks
 {

    private var mMovie: Movie? = null
    //private var tTV: TV? = null
    //private var mVideoAdapter: VideoAdapter? = null
    //private var mReviewAdapter: ReviewAdapter? = null
    //private var mDb: SQLiteDatabase? = null

    internal var ivBackdrop: ImageView? = null
    internal var ivPoster: ImageView? = null
    internal var tvDescription: TextView? = null
    internal var tvReleaseDateHeading: TextView? = null
    internal var tvReleaseDate: TextView? = null
    internal var rbRating: RatingBar? = null
    internal var tvVideosHeading: TextView? = null
    internal var rvVideoList: RecyclerView? = null
    internal var tvReviewsHeading: TextView? = null
    internal var rvReviews: RecyclerView? = null

    internal var mFavoriteButton: Button? = null

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
        //rvVideoList = findViewById(R.id.video_list) as RecyclerView
        tvReviewsHeading = findViewById<View>(R.id.reviews_heading) as TextView
        //rvReviews = findViewById(R.id.reviews) as RecyclerView
        //mFavoriteButton = findViewById(R.id.favorite_button) as Button

/*
        val intent = intent
        if (intent == null) {
            closeOnError("Intent is null")
        }

        val data = getIntent().extras
        if (data == null) {
            closeOnError(getString(R.string.Data_Not_Found))
            return
        }

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = data.getParcelable<Movie>(EXTRA_MOVIE)
            if (mMovie == null) {
                closeOnError(getString(R.string.Data_Not_Found))
                return
            }
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            tTV = data.getParcelable<TV>(EXTRA_TV)
            if (tTV == null) {
                closeOnError(getString(R.string.Data_Not_Found))
                return
            }
        } else {
            throw IllegalArgumentException("Detail activity must receive a movie or TV parcelable")
        }
*/

        val tbToolbar = findViewById<View>(R.id.tbToolbar) as Toolbar
        setSupportActionBar(tbToolbar)
        val ctlToolbarLayout = findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
//        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            ctlToolbarLayout.title = mMovie!!.getTitle()
/*
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            ctlToolbarLayout.title = tTV!!.getTitle()
        }
*/
        ctlToolbarLayout.setExpandedTitleColor(Color.WHITE)
        ctlToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)

        val llMovieLayout = LinearLayout(applicationContext)
        llMovieLayout.orientation = LinearLayout.HORIZONTAL

        //ButterKnife.bind(this)

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            strDescription = mMovie!!.getDescription()
            tvReleaseDateHeading?.text = "Release Date"
            strReleaseDate = mMovie!!.getReleaseDate()
            dUserRating = mMovie!!.getUserRating()
            strPoster = mMovie!!.getPoster()
            strBackdrop = mMovie!!.getBackdrop()
/*
            if (!mMovie!!.isFavorite()) {
                mFavoriteButton.setText(R.string.favorite)
            } else {
                mFavoriteButton.setText(R.string.unfavorite)
            }
*/
/*
        } else if (getIntent().hasExtra(EXTRA_TV)) {
            strDescription = tTV!!.getDescription()
            tvReleaseDateHeading?.text = "First Air Date"
            strReleaseDate = tTV!!.getReleaseDate()
            dUserRating = tTV!!.getUserRating()
            strPoster = tTV!!.getPoster()
            strBackdrop = tTV!!.getBackdrop()
            if (!tTV!!.isFavorite()) {
                mFavoriteButton.setText(R.string.favorite)
            } else {
                mFavoriteButton.setText(R.string.unfavorite)
            }

        }
*/

        tvDescription?.text = strDescription
        tvReleaseDate?.text = strReleaseDate
        rbRating?.rating = dUserRating.toFloat()

        // For horizontal list of trailers
/*
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rvVideoList.layoutManager = layoutManager
        mVideoAdapter = VideoAdapter(ArrayList<Video>(), this)
        rvVideoList.adapter = mVideoAdapter
        rvVideoList.isNestedScrollingEnabled = false

        // Fetch trailers only if savedInstanceState == null
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_VIDEOS)) {
            val videos = savedInstanceState.getParcelableArrayList<Video>(EXTRA_VIDEOS)
            mVideoAdapter!!.addVideo(videos)
        } else {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                fetchTrailers(java.lang.Long.parseLong(mMovie!!.getId()))
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                fetchTrailers(java.lang.Long.parseLong(tTV!!.getId()))
            }
        }

        // For vertical list of reviews
        val llmReviews = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvReviews.layoutManager = llmReviews
        mReviewAdapter = ReviewAdapter(ArrayList<Review>(), this as ReviewAdapter.Callbacks)
        rvReviews.adapter = mReviewAdapter

        // Fetch reviews only if savedInstanceState == null
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_REVIEWS)) {
            val reviews = savedInstanceState.getParcelableArrayList<Review>(EXTRA_REVIEWS)
            mReviewAdapter!!.add(reviews)
        } else {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                fetchReviews(java.lang.Long.parseLong(mMovie!!.getId()))
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                fetchReviews(java.lang.Long.parseLong(tTV!!.getId()))
            }
        }

        val pmDbHelper = MovieTVDbHelper(this)
        mDb = pmDbHelper.getWritableDatabase()

        mFavoriteButton.setOnClickListener {
            if (getIntent().hasExtra(EXTRA_MOVIE)) {
                if (mMovie!!.isFavorite()) {
                    mMovie!!.setFavorite(false)
                    mFavoriteButton.setText(R.string.favorite)
                    removeFromFavorites()
                } else {
                    mMovie!!.setFavorite(true)
                    mFavoriteButton.setText(R.string.unfavorite)
                    addToFavorites()
                }
            } else if (getIntent().hasExtra(EXTRA_TV)) {
                if (tTV!!.isFavorite()) {
                    tTV!!.setFavorite(false)
                    mFavoriteButton.setText(R.string.favorite)
                    removeFromFavorites()
                } else {
                    tTV!!.setFavorite(true)
                    mFavoriteButton.setText(R.string.unfavorite)
                    addToFavorites()
                }
            }
        }
*/

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

/*
    private fun fetchTrailers(lMovieId: Long) {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { request -> request.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java!!)
        if (intent.hasExtra(EXTRA_MOVIE)) {
            mtaService.getMovieVideos(lMovieId, object : Callback<Video.VideoResult> {
                override fun success(videoResult: Video.VideoResult, response: Response) {
                    mVideoAdapter!!.setVideoList(videoResult.getVideoList())
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        } else if (intent.hasExtra(EXTRA_TV)) {
            mtaService.getTVVideos(lMovieId, object : Callback<Video.VideoResult> {
                override fun success(videoResult: Video.VideoResult, response: Response) {
                    mVideoAdapter!!.setVideoList(videoResult.getVideoList())
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        }
    }

    private fun fetchReviews(lMovieId: Long) {
        val raAdapter = RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor { request -> request.addEncodedQueryParam("api_key", getText(R.string.api_key).toString()) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
        val mtaService = raAdapter.create<MovieTVAPI>(MovieTVAPI::class.java!!)
        if (intent.hasExtra(EXTRA_MOVIE)) {
            mtaService.getMovieReviews(lMovieId, object : Callback<Review.ReviewResult> {
                override fun success(reviewResult: Review.ReviewResult, response: Response) {
                    mReviewAdapter!!.setReviews(reviewResult.getReviews())
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        } else if (intent.hasExtra(EXTRA_TV)) {
            mtaService.getTVReviews(lMovieId, object : Callback<Review.ReviewResult> {
                override fun success(reviewResult: Review.ReviewResult, response: Response) {
                    mReviewAdapter!!.setReviews(reviewResult.getReviews())
                }

                override fun failure(error: RetrofitError) {
                    error.printStackTrace()
                }
            })
        }
    }
*/

    private fun closeOnError(msg: String) {
        finish()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
/*

    private fun addToFavorites() {
        val cv = ContentValues()
        if (intent.hasExtra(EXTRA_MOVIE)) {
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_ID, mMovie!!.getId())
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_ORIGINALTITLE, mMovie!!.getTitle())
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_OVERVIEW, mMovie!!.getDescription())
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_POSTERPATH, mMovie!!.getPoster())
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_BACKDROP, mMovie!!.getBackdrop())
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_RELEASEDATE, mMovie!!.getReleaseDate())
            cv.put(MovieTVDBContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE, mMovie!!.getUserRating())

            val rowCount = mDb!!.insert(MovieTVDBContract.MovieEntry.TABLE_NAME, null, cv)
        } else if (intent.hasExtra(EXTRA_TV)) {
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_ID, tTV!!.getId())
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_ORIGINALTITLE, tTV!!.getTitle())
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_OVERVIEW, tTV!!.getDescription())
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_POSTERPATH, tTV!!.getPoster())
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_BACKDROP, tTV!!.getBackdrop())
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_RELEASEDATE, tTV!!.getReleaseDate())
            cv.put(MovieTVDBContract.TVEntry.COLUMN_NAME_VOTEAVERAGE, tTV!!.getUserRating())

            val rowCount = mDb!!.insert(MovieTVDBContract.TVEntry.TABLE_NAME, null, cv)

        }
    }

    private fun removeFromFavorites() {
        if (intent.hasExtra(EXTRA_MOVIE)) {
            var uri = MovieTVDBContract.MovieEntry.CONTENT_URI
            uri = uri.buildUpon().appendPath(mMovie!!.getId().toString()).build()
            val rowCount = contentResolver.delete(uri, null, null)
        } else if (intent.hasExtra(EXTRA_TV)) {
            var uri = MovieTVDBContract.TVEntry.CONTENT_URI
            uri = uri.buildUpon().appendPath(tTV!!.getId().toString()).build()
            val rowCount = contentResolver.delete(uri, null, null)

        }
    }

    fun watch(video: Video, nPosition: Int) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getKey())))
    }

    fun read(review: Review, position: Int) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl())))
    }

*/
    companion object {
        val EXTRA_MOVIE = "movie"
        val EXTRA_TV = "tv"
        val EXTRA_VIDEOS = "video"
        val EXTRA_REVIEWS = "review"
        val TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500"
    }
}