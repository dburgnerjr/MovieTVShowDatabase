package com.danielburgnerjr.movietvshowdatabase.features.movies

/**
 * Created by dburgnerjr on 6/4/17.
 */
import com.danielburgnerjr.movietvshowdatabase.api.RestAPI
import com.danielburgnerjr.movietvshowdatabase.commons.Movie
import rx.Observable
import rx.Observable.create

/**
 * Movie Manager allows you to request more movies and TV shows from the Movie Database API
 *
 * @author dburgnerjr
 */
class MovieManager(private val api: RestAPI = RestAPI()) {

    fun getMovie(): Observable<List<Movie>> {
        return create {
            subscriber ->
            val callResponse = api.getMovie()
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val movie = response.body().data.children.map {
                    val item = it.data
                    Movie(item.title, item.poster, item.description, item.backdrop)
                }
                subscriber.onNext(movie)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}
