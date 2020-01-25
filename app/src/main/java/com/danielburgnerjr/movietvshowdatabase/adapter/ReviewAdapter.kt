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

class ReviewAdapter(private val reviewList: ArrayList<Review>, private val mCallbacks: Callbacks) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    interface Callbacks {
        fun read(review: Review, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.reviews_list, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]

        holder.mReview = review
        holder.mAuthorView.text = review.author
        holder.mContentView.text = review.content

        holder.mView.setOnClickListener { mCallbacks.read(review, holder.adapterPosition) }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    inner class ReviewViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        internal var mAuthorView: TextView = mView.findViewById<View>(R.id.review_author) as TextView
        internal var mContentView: TextView = mView.findViewById<View>(R.id.review_content) as TextView
        var mReview: Review? = null

        init {
            ButterKnife.bind(this, mView)
        }
    }

    fun add(reviews: List<Review>?) {
        reviewList.clear()
        reviews?.let { reviewList.addAll(it) }
        notifyDataSetChanged()
    }

    fun setReviews(mReview: List<Review>) {
        reviewList.clear()
        reviewList.addAll(mReview)
        notifyDataSetChanged()
    }
}
