package com.danielburgnerjr.movietvshowdatabase.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.danielburgnerjr.movietvshowdatabase.R
import com.danielburgnerjr.movietvshowdatabase.model.TV
import com.danielburgnerjr.movietvshowdatabase.MovieDetailActivity


import com.squareup.picasso.Picasso

import java.util.ArrayList

class TVAdapter(private val conContext: Context) : RecyclerView.Adapter<MovieViewHolder>() {
    private var mTVList: MutableList<TV>? = null
    private val liInflater: LayoutInflater = LayoutInflater.from(conContext)

    override fun onCreateViewHolder(vgParent: ViewGroup, nViewType: Int): MovieViewHolder {
        val vView = liInflater.inflate(R.layout.movie_item, vgParent, false)
        val mvhHolder = MovieViewHolder(vView)
        vView.setOnClickListener{
            val nPos = mvhHolder.adapterPosition
            val intI = Intent(conContext, MovieDetailActivity::class.java)
            intI.putExtra(MovieDetailActivity.EXTRA_TV, mTVList!![nPos])
            conContext.startActivity(intI)
        }
        return mvhHolder
    }

    override fun onBindViewHolder(mvhH: MovieViewHolder, nP: Int) {
        val tT = mTVList!![nP]
        Picasso.get()
                .load(TMDB_IMAGE_PATH + tT.poster)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(mvhH.ivImageView)
    }

    override fun getItemCount(): Int {
        return if (mTVList == null) 0 else mTVList!!.size
    }

    fun setTVList(tl: List<TV>?) {
        this.mTVList = ArrayList()
        tl?.let { this.mTVList!!.addAll(tl) }
        notifyDataSetChanged()
    }

    companion object {
        const val TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500"
    }
}
