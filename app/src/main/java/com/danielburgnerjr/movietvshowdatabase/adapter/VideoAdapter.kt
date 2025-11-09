package com.danielburgnerjr.movietvshowdatabase.adapter

import android.content.Context
import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.danielburgnerjr.movietvshowdatabase.R
import com.danielburgnerjr.movietvshowdatabase.model.Video

import com.squareup.picasso.Picasso

import java.util.ArrayList

class VideoAdapter(private val mVideoList: ArrayList<Video>?, private val mCallbacks: Callbacks) : RecyclerView.Adapter<VideoViewHolder>() {
    private var mInflater: LayoutInflater? = null
    private var mContext: Context? = null

    interface Callbacks {
        fun watch(video: Video, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, nViewType: Int): VideoViewHolder {
        mInflater = LayoutInflater.from(parent.context)
        val vView = mInflater!!.inflate(R.layout.videos_list, parent, false)
        return VideoViewHolder(vView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = mVideoList!![position]
        mContext = holder.vView.context

        var paddingLeft = 0f
        if (position == 0) {
            paddingLeft = mContext!!.resources.getDimension(R.dimen.detail_horizontal_padding)
        }

        var paddingRight = 0f
        if (position + 1 != itemCount) {
            paddingRight = mContext!!.resources.getDimension(R.dimen.detail_horizontal_padding) / 2
        }

        holder.vView.setPadding(paddingLeft.toInt(), 0, paddingRight.toInt(), 0)

        holder.viVideo = video

        val thumbnailUrl = "http://img.youtube.com/vi/" + video.key + "/0.jpg"

        Picasso.get()
                .load(thumbnailUrl)
                .config(Bitmap.Config.RGB_565)
                .into(holder.ivThumbnailView)

        holder.vView.setOnClickListener { mCallbacks.watch(video, holder.bindingAdapterPosition) }
    }

    override fun getItemCount(): Int {
        return mVideoList?.size ?: 0
    }

    fun addVideo(mVideo: List<Video>?) {
        mVideoList!!.clear()
        mVideo?.let { mVideoList.addAll(it) }
        notifyDataSetChanged()
    }

    fun setVideoList(mVideo: List<Video>) {
        mVideoList!!.clear()
        mVideoList.addAll(mVideo)
        notifyDataSetChanged()
    }
}
