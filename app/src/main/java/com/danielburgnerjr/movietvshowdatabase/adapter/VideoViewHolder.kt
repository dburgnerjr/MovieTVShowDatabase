package com.danielburgnerjr.movietvshowdatabase.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView

import butterknife.ButterKnife

import com.danielburgnerjr.movietvshowdatabase.R
import com.danielburgnerjr.movietvshowdatabase.model.Video

class VideoViewHolder(val vView: View) : RecyclerView.ViewHolder(vView) {
    var ivThumbnailView: ImageView = vView.findViewById<View>(R.id.trailer_thumbnail) as ImageView
    var viVideo: Video? = null

    init {
        ButterKnife.bind(this, vView)
    }
}
