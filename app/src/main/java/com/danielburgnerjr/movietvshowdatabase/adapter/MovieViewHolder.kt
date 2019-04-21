package com.danielburgnerjr.movietvshowdatabase.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

import com.danielburgnerjr.movietvshowdatabase.R

class MovieViewHolder(vItemView: View) : RecyclerView.ViewHolder(vItemView) {
    var ivImageView: ImageView

    init {
        ivImageView = vItemView.findViewById(R.id.ivImageView) as ImageView
    }
}
