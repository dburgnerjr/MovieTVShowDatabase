package com.danielburgnerjr.movietvshowdatabase.model

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Video : Parcelable {

    @SerializedName("id")
    var strID: String? = ""

    @SerializedName("key")
    var key: String? = ""

    @SerializedName("name")
    var name: String? = ""

    @SerializedName("site")
    var strSite: String? = ""

    @SerializedName("size")
    var strSize: String? = ""

    var videoUrl: String = "http://www.youtube.com/watch?v=" + key!!

    constructor() {}

    constructor(strID: String, strK: String, strN: String, strSt: String, strSz: String) {
        this.strID = strID
        this.key = strK
        this.name = strN
        this.strSite = strSt
        this.strSize = strSz
    }

    protected constructor(`in`: Parcel) {
        strID = `in`.readString()
        key = `in`.readString()
        name = `in`.readString()
        strSite = `in`.readString()
        strSize = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strID)
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(strSite)
        parcel.writeString(strSize)
    }

    class VideoResult {
        @SerializedName("results")
        val videoList: List<Video> = ArrayList()
    }

    companion object {

        val LOG_TAG = Video::class.java.simpleName

        @JvmField
        val CREATOR: Parcelable.Creator<Video> = object : Parcelable.Creator<Video> {
            override fun createFromParcel(`in`: Parcel): Video {
                return Video(`in`)
            }

            override fun newArray(size: Int): Array<Video?> {
                return arrayOfNulls(size)
            }
        }
    }
}
