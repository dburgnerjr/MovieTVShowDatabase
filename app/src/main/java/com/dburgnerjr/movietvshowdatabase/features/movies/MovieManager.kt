package com.dburgnerjr.movietvshowdatabase.features.movies

/**
 * Created by dburgnerjr on 6/4/17.
 */
import com.dburgnerjr.movietvshowdatabase.commons.Movie
import rx.Observable

/**
 * Movie Manager allows you to request more movies and TV shows from the Movie Database API
 *
 * @author dburgnerjr
 */
class MovieManager() {

    fun getMovie(): Observable<List<Movie>> {
        return Observable.create {
            subscriber ->

            val movie = mutableListOf<Movie>()
            for (nI in 1..10) {
                movie.add(Movie(
                        "Title",
                        "http://lorempixel.com/200/200/technics/$nI",
                        "Description",
                        "Backdrop"
                ))
            }
            subscriber.onNext(movie)
        }
    }
}
