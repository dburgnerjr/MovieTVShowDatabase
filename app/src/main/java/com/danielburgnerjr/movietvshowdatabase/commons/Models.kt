package com.danielburgnerjr.movietvshowdatabase.commons

/**
 * Created by dburgnerjr on 6/4/17.
 */
import android.os.Parcel
import android.os.Parcelable
import com.danielburgnerjr.movietvshowdatabase.commons.adapter.AdapterConstants
import com.danielburgnerjr.movietvshowdatabase.commons.adapter.ViewType
import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("id") val strID: String,
        @SerializedName("title") val strTitle: String,
        @SerializedName("poster_path") val strPoster: String,
        @SerializedName("overview") val strDescription: String,
        @SerializedName("backdrop_path") val strBackdrop: String,
        @SerializedName("release_date") val strReleaseDate: String,
        @SerializedName("vote_average") val dUserRating: Double,
        val isFavorite: Boolean
) : ViewType, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun getViewType() = AdapterConstants.MOVIE
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strID)
        parcel.writeString(strTitle)
        parcel.writeString(strPoster)
        parcel.writeString(strDescription)
        parcel.writeString(strBackdrop)
        parcel.writeString(strReleaseDate)
        parcel.writeDouble(dUserRating)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}

data class TV(
        @SerializedName("id") val strID: String,
        @SerializedName("title") val strTitle: String,
        @SerializedName("poster_path") val strPoster: String,
        @SerializedName("overview") val strDescription: String,
        @SerializedName("backdrop_path") val strBackdrop: String,
        @SerializedName("first_air_date") val strReleaseDate: String,
        @SerializedName("vote_average") val dUserRating: Double,
        val isFavorite: Boolean
) : ViewType, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun getViewType() = AdapterConstants.TV
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strID)
        parcel.writeString(strTitle)
        parcel.writeString(strPoster)
        parcel.writeString(strDescription)
        parcel.writeString(strBackdrop)
        parcel.writeString(strReleaseDate)
        parcel.writeDouble(dUserRating)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TV> {
        override fun createFromParcel(parcel: Parcel): TV {
            return TV(parcel)
        }

        override fun newArray(size: Int): Array<TV?> {
            return arrayOfNulls(size)
        }
    }
}
