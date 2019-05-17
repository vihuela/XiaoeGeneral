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
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson

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
        return inputMethodManager.hideSoftInputFromWindow(view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
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

fun ImageView.show(imageUrl:String?){
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
            .error(ColorDrawable(Color.parseColor("#DCDDE1")))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
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


