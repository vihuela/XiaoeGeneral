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

package com.xiaoe.app2.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


//BindingAdapter需要app module执行apt，不能放于lib module
@BindingAdapter("imageFromUrl")
fun loadImage(iv: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(iv.context)
            .load(imageUrl)
            .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
            .error(ColorDrawable(Color.parseColor("#DCDDE1")))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(iv)
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean?) {
    view.isVisible = isGone == null || isGone
}

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}




