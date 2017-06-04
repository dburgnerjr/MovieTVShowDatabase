package com.dburgnerjr.movietvshowdatabase.features.movies.adapter

/**
 * Created by dburgnerjr on 6/4/17.
 */
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.dburgnerjr.movietvshowdatabase.commons.adapter.ViewType
import com.dburgnerjr.movietvshowdatabase.commons.adapter.ViewTypeDelegateAdapter
import com.dburgnerjr.movietvshowdatabase.R
import com.dburgnerjr.movietvshowdatabase.commons.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.movie_item_loading)) {
    }
}
