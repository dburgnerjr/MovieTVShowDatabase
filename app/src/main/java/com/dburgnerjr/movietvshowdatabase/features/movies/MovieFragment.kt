package com.dburgnerjr.movietvshowdatabase.features.movies

/**
 * Created by dburgnerjr on 6/4/17.
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dburgnerjr.movietvshowdatabase.R
import com.dburgnerjr.movietvshowdatabase.commons.Movie
import com.dburgnerjr.movietvshowdatabase.commons.extensions.inflate
import com.dburgnerjr.movietvshowdatabase.features.movies.adapter.MovieAdapter
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.movie_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movie_list.setHasFixedSize(true)
        movie_list.layoutManager = LinearLayoutManager(context)
        initAdapter()

        if (savedInstanceState == null) {
            val movie = mutableListOf<Movie>()
            for (nI in 1..10) {
                movie.add(Movie(
                        "Title",
                        "http://lorempixel.com/200/200/technics/$nI",
                        "Description",
                        "Backdrop"
                ))
            }
            (movie_list.adapter as MovieAdapter).addMovie(movie)
        }
    }

    private fun initAdapter() {
        if (movie_list.adapter == null) {
            movie_list.adapter = MovieAdapter()
        }
    }
}
