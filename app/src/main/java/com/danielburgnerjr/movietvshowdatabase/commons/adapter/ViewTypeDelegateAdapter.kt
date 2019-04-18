package com.danielburgnerjr.movietvshowdatabase.commons.adapter

/**
 * Created by dburgnerjr on 6/4/17.
 */

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}