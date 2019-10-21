package com.danielburgnerjr.movietvshowdatabase.model

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Video : Parcelable {

    @SerializedName("id")
    var strID: String? = null

    @SerializedName("key")
    var key: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("site")
    var strSite: String? = null

    @SerializedName("size")
    var strSize: String? = null

    var videoUrl: String? = "http://www.youtube.com/watch?v=" + key!!

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
            override fun createFromParcel(source: Parcel): Video {
                val video = Video()
                video.strID = source.readString()
                video.key = source.readString()
                video.name = source.readString()
                video.strSite = source.readString()
                video.strSize = source.readString()
                return video
            }

            override fun newArray(size: Int): Array<Video?> {
                return arrayOfNulls(size)
            }
        }
    }
}
