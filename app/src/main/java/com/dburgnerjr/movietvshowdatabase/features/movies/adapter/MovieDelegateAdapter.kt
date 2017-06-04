package com.dburgnerjr.movietvshowdatabase.features.movies.adapter

/**
 * Created by dburgnerjr on 6/4/17.
 */
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.dburgnerjr.movietvshowdatabase.R
import com.dburgnerjr.movietvshowdatabase.commons.Movie
import com.dburgnerjr.movietvshowdatabase.commons.adapter.ViewType
import com.dburgnerjr.movietvshowdatabase.commons.adapter.ViewTypeDelegateAdapter
import com.dburgnerjr.movietvshowdatabase.commons.extensions.inflate
import com.dburgnerjr.movietvshowdatabase.commons.extensions.loadImg
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as Movie)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.movie_item)) {

        fun bind(item: Movie) = with(itemView) {
            img_thumbnail.loadImg(item.strPoster)
            description.text = item.strDescription
            title.text = item.strTitle
            backdrop.text = item.strBackdrop
        }
    }
}
