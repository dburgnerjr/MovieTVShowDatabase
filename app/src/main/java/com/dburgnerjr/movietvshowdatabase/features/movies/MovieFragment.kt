package com.dburgnerjr.movietvshowdatabase.features.movies

/**
 * Created by dburgnerjr on 6/4/17.
 */

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dburgnerjr.movietvshowdatabase.R
import com.dburgnerjr.movietvshowdatabase.commons.RxBaseFragment
import com.dburgnerjr.movietvshowdatabase.commons.extensions.inflate
import com.dburgnerjr.movietvshowdatabase.features.movies.adapter.MovieAdapter
import kotlinx.android.synthetic.main.movie_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MovieFragment : RxBaseFragment() {

    private val movieManager by lazy { MovieManager() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.movie_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movie_list.setHasFixedSize(true)
        movie_list.layoutManager = LinearLayoutManager(context)
        initAdapter()

        if (savedInstanceState == null) {
            requestMovie()
        }
    }

    private fun requestMovie() {
        val subscription = movieManager.getMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { retrievedMovie ->
                            (movie_list.adapter as MovieAdapter).addMovie(retrievedMovie)
                        },
                        { e ->
                            Snackbar.make(movie_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                        }
                )
        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (movie_list.adapter == null) {
            movie_list.adapter = MovieAdapter()
        }
    }
}
