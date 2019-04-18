package com.danielburgnerjr.movietvshowdatabase.features.movies.adapter

/**
 * Created by dburgnerjr on 6/4/17.
 */
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.danielburgnerjr.movietvshowdatabase.R
import com.danielburgnerjr.movietvshowdatabase.commons.Movie
import com.danielburgnerjr.movietvshowdatabase.commons.adapter.ViewType
import com.danielburgnerjr.movietvshowdatabase.commons.adapter.ViewTypeDelegateAdapter
import com.danielburgnerjr.movietvshowdatabase.commons.extensions.inflate
import com.danielburgnerjr.movietvshowdatabase.commons.extensions.loadImg
import kotlinx.android.synthetic.main.content_movie.view.*

class MovieDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as Movie)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.content_movie)) {

        fun bind(item: Movie) = with(itemView) {
            movie_poster.loadImg(item.strPoster)
            movie_description.text = item.strDescription
            release_date.text = item.strReleaseDate
            rating.rating = item.dUserRating.toFloat()
            title.text = item.strTitle
            backdrop.text = item.strBackdrop
        }
    }
}
