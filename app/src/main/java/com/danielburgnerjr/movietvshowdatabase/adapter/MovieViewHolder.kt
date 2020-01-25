package com.danielburgnerjr.movietvshowdatabase.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView

import com.danielburgnerjr.movietvshowdatabase.R

class MovieViewHolder(vItemView: View) : RecyclerView.ViewHolder(vItemView) {
    var ivImageView: ImageView = vItemView.findViewById(R.id.ivImageView) as ImageView
}
