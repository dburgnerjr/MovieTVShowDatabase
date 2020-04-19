package com.danielburgnerjr.movietvshowdatabase.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.danielburgnerjr.movietvshowdatabase.R
import com.danielburgnerjr.movietvshowdatabase.model.Movie
import com.danielburgnerjr.movietvshowdatabase.MovieDetailActivity

import com.squareup.picasso.Picasso

import java.util.ArrayList

class MovieAdapter(private val conContext: Context) : RecyclerView.Adapter<MovieViewHolder>() {
    private var mMovieList: MutableList<Movie>? = null
    private val liInflater: LayoutInflater = LayoutInflater.from(conContext)

    override fun onCreateViewHolder(vgParent: ViewGroup, nViewType: Int): MovieViewHolder {
        val vView = liInflater.inflate(R.layout.movie_item, vgParent, false)
        val mvhHolder = MovieViewHolder(vView)
        vView.setOnClickListener{
            val nPos = mvhHolder.adapterPosition
            val intI = Intent(conContext, MovieDetailActivity::class.java)
            intI.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList!![nPos])
            conContext.startActivity(intI)
        }
        return mvhHolder
    }

    override fun onBindViewHolder(mvhH: MovieViewHolder, nP: Int) {
        val mM = mMovieList!![nP]
        Picasso.get()
                .load(TMDB_IMAGE_PATH + mM.poster)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)
                .into(mvhH.ivImageView)
    }

    override fun getItemCount(): Int {
        return if (mMovieList == null) 0 else mMovieList!!.size
    }

    fun setMovieList(ml: List<Movie>?) {
        this.mMovieList = ArrayList()
        ml?.let { this.mMovieList?.addAll(it) }
        notifyDataSetChanged()
    }

    companion object {
        const val TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500"
    }
}
