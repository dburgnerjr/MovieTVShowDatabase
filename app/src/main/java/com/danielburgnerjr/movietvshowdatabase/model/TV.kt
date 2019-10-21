package com.danielburgnerjr.movietvshowdatabase.model

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

class TV : Parcelable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var title: String? = null

    @SerializedName("poster_path")
    var poster: String? = null

    @SerializedName("overview")
    var description: String? = null

    @SerializedName("backdrop_path")
    var backdrop: String? = null

    @SerializedName("first_air_date")
    var releaseDate: String? = null

    @SerializedName("vote_average")
    var userRating: Double = 0.toDouble()

    var isFavorite = false

    constructor() {}

    constructor(strID: String, strT: String, strD: String, strP: String, strBD: String, strRD: String, dVA: Double, bF: Boolean) {
        this.id = strID
        this.title = strT
        this.description = strD
        this.poster = strP
        this.backdrop = strBD
        this.releaseDate = strRD
        this.userRating = dVA
        this.isFavorite = bF
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        title = `in`.readString()
        poster = `in`.readString()
        description = `in`.readString()
        backdrop = `in`.readString()
        releaseDate = `in`.readString()
        userRating = `in`.readDouble()
        isFavorite = `in`.readByte().toInt() != 0
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parP: Parcel, nI: Int) {
        parP.writeString(id)
        parP.writeString(title)
        parP.writeString(poster)
        parP.writeString(description)
        parP.writeString(backdrop)
        parP.writeString(releaseDate)
        parP.writeDouble(userRating)
        parP.writeByte((if (isFavorite) 1 else 0).toByte())
    }

    class TVResult {
        @SerializedName("results")
        val results: List<TV>? = null

        val size: Int
            get() = results!!.size
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<TV> = object : Parcelable.Creator<TV> {
            override fun createFromParcel(`in`: Parcel): TV {
                return TV(`in`)
            }

            override fun newArray(nSize: Int): Array<TV?> {
                return arrayOfNulls(nSize)
            }
        }
    }
}
