/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package com.xiaoe.common.ext.ext

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.ViewConfiguration
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.hitomi.tilibrary.loader.ImageLoader
import com.hitomi.tilibrary.style.index.NumberIndexIndicator
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import java.io.File

//toast
fun Any.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    when (length) {
        Toast.LENGTH_SHORT -> {
            ToastUtils.showShort(msg)
        }
        Toast.LENGTH_LONG -> {
            ToastUtils.showLong(msg)
        }
    }
}

fun Activity.hideKeyboard(): Boolean {
    val view = currentFocus
    view?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    return false
}

//Application
fun Application.getRunningProcessList(): MutableList<ActivityManager.RunningAppProcessInfo>? {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return am.runningAppProcesses
}

fun Application.getProcessNames(): String? {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return am.runningAppProcesses
        ?.find { it.pid == android.os.Process.myPid() }
        ?.processName
}

fun Application.killCurrentProcess() {
    android.os.Process.killProcess(android.os.Process.myPid())
}

fun ImageView.show(imageUrl: Any?) {
    try {
        tag = null
        Glide.with(context)
            .load(imageUrl)
            .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
            .error(ColorDrawable(Color.parseColor("#DCDDE1")))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun ImageView.zoom(url: String) {//网络url或者本地File路径
    if (url.isNotBlank() && drawable !is ColorDrawable) {
        //url
        val tarR = when {
            //不是网络路径即本地路径，没有file打头给加上
            !url.startsWith("http") && !url.startsWith("file") -> "file://$url"
            else -> url
        }
        Transferee.getDefault(context).apply(
            TransferConfig.build().setImageLoader(GlideImageLoader(context)).bindImageView
                (this, tarR)
        ).show()
    }
}

fun ImageView.zoom(url: List<String>, rv: RecyclerView, @IdRes itemIVId: Int) {//网络url或者本地File路径
    val tarR = arrayListOf<String>()
    url.forEach {
        val tUrl: String = when {
            //不是网络路径即本地路径，没有file打头给加上
            !it.startsWith("http") && !it.startsWith("file") -> "file://$it"
            else -> it
        }
        tarR.add(tUrl)
    }
    val config = TransferConfig.build()
        .setSourceImageList(tarR)
        .setProgressIndicator(ProgressBarIndicator())
        .setIndexIndicator(NumberIndexIndicator())
        .setJustLoadHitImage(true)
        .setImageLoader(GlideImageLoader(context))
        .bindRecyclerView(rv, itemIVId)
    Transferee.getDefault(context).apply(config).show()
}

fun RecyclerView.addOnVerticalScrollListener(onScrolledUp: (recyclerView: RecyclerView) -> Unit,
                                             onScrolledDown: (recyclerView: RecyclerView) -> Unit,
                                             onScrolledToTop: (recyclerView: RecyclerView) -> Unit,
                                             onScrolledToBottom: (recyclerView: RecyclerView) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (!recyclerView.canScrollVertically(-1)) {
                onScrolledUp.invoke(recyclerView)
            } else if (!recyclerView.canScrollVertically(1)) {
                onScrolledDown.invoke(recyclerView)
            } else if (dy < 0 && Math.abs(dy) > ViewConfiguration.get(context).scaledTouchSlop) {
                onScrolledToTop.invoke(recyclerView)
            } else if (dy > 0 && Math.abs(dy) > ViewConfiguration.get(context).scaledTouchSlop) {
                onScrolledToBottom.invoke(recyclerView)
            }
        }
    })

}

fun ViewPager.setupAdapter(frgManager: FragmentManager, vararg  frg:Fragment?){
    val targetF = frg.filter { it!=null }
    if(targetF.isNotEmpty()){
        offscreenPageLimit = frg.size
        adapter = object : FragmentPagerAdapter(frgManager){
            override fun getItem(position: Int): Fragment {
                return targetF[position]!!
            }
            override fun getCount(): Int  = targetF.size
        }
    }

}

private class GlideImageLoader(val context: Context) : ImageLoader {

    override fun showImage(
        srcUrl: String,
        imageView: ImageView,
        placeholder: Drawable,
        sourceCallback: ImageLoader.SourceCallback
    ) {
        Glide.with(context)
            .load(srcUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    sourceCallback.onDelivered(ImageLoader.STATUS_DISPLAY_SUCCESS)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    sourceCallback.onDelivered(ImageLoader.STATUS_DISPLAY_SUCCESS)
                    return false
                }
            })
            .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
            .error(ColorDrawable(Color.parseColor("#DCDDE1")))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    override fun loadImageSync(imageUrl: String): Drawable {
        return Glide.with(context)
            .load(imageUrl)
            .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
            .error(ColorDrawable(Color.parseColor("#DCDDE1")))
            .transition(DrawableTransitionOptions.withCrossFade()).submit().get()
    }

    override fun isLoaded(String: String): Boolean {
        return false
    }

    override fun clearCache() {

    }

    override fun loadImageAsync(imageUrl: String, callback: ImageLoader.ThumbnailCallback) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
            .error(ColorDrawable(Color.parseColor("#DCDDE1")))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(object : CustomTarget<Drawable>() {

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    callback.onFinish(errorDrawable)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    callback.onFinish(resource)
                }

            })
    }
}

//gson
inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, T::class.java)
}

//log
inline fun <reified T> T.L(message: Any, tag: String = T::class.java.simpleName) {
    Log.d(tag, message.toString())
}


