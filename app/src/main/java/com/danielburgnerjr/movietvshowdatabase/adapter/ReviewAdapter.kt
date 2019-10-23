package com.danielburgnerjr.movietvshowdatabase.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import butterknife.ButterKnife

import com.danielburgnerjr.movietvshowdatabase.R
import com.danielburgnerjr.movietvshowdatabase.model.Review

class ReviewAdapter(val reviews: ArrayList<Review>, private val mCallbacks: Callbacks) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    interface Callbacks {
        fun read(review: Review, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.reviews_list, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        holder.mReview = review
        holder.mAuthorView.text = review.author
        holder.mContentView.text = review.content

        holder.mView.setOnClickListener { mCallbacks.read(review, holder.adapterPosition) }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class ReviewViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        internal var mAuthorView: TextView
        internal var mContentView: TextView
        var mReview: Review? = null

        init {
            mAuthorView = mView.findViewById<View>(R.id.review_author) as TextView
            mContentView = mView.findViewById<View>(R.id.review_content) as TextView
            ButterKnife.bind(this, mView)
        }
    }

    fun add(reviews: List<Review>?) {
        this.reviews.clear()
        this.reviews?.let { this.reviews.addAll(it) }
        notifyDataSetChanged()
    }

    fun setReviews(mReview: List<Review>) {
        reviews.clear()
        reviews.addAll(mReview)
        notifyDataSetChanged()
    }
}
