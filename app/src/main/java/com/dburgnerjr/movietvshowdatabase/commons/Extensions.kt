@file:JvmName("ExtensionsUtils")

package com.dburgnerjr.movietvshowdatabase.commons

/**
 * Created by dburgnerjr on 6/4/17.
 */
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(nLayoutId: Int, bAttachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(nLayoutId, this, bAttachToRoot)
}
