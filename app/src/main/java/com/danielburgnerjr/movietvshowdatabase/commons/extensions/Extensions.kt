@file:JvmName("ExtensionsUtils")

package com.danielburgnerjr.movietvshowdatabase.commons.extensions

/**
 * Created by dburgnerjr on 6/4/17.
 */
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.danielburgnerjr.movietvshowdatabase.R
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(nLayoutId: Int, bAttachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(nLayoutId, this, bAttachToRoot)
}

fun ImageView.loadImg(strImageUrl: String) {
    if (TextUtils.isEmpty(strImageUrl)) {
        Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    } else {
        Picasso.with(context).load(strImageUrl).into(this)
    }
}
