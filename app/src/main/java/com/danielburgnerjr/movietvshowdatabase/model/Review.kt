package com.danielburgnerjr.movietvshowdatabase.model

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

@Suppress("unused")
class Review : Parcelable {

    @SerializedName("id")
    var strID: String? = null

    @SerializedName("author")
    var author: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("url")
    var url: String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strID)
        parcel.writeString(author)
        parcel.writeString(content)
        parcel.writeString(url)
    }

    class ReviewResult {
        @SerializedName("results")
        val reviews: List<Review> = ArrayList()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Review> = object : Parcelable.Creator<Review> {
            override fun createFromParcel(source: Parcel): Review {
                val review = Review()
                review.strID = source.readString()
                review.author = source.readString()
                review.content = source.readString()
                review.url = source.readString()
                return review
            }

            override fun newArray(size: Int): Array<Review?> {
                return arrayOfNulls(size)
            }
        }
    }
}
