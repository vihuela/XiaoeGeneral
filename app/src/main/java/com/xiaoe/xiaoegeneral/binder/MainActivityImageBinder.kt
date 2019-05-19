package com.xiaoe.xiaoegeneral.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import com.xiaoe.common.ext.ext.show
import com.xiaoe.common.ext.ext.zoom
import com.xiaoe.xiaoegeneral.R
import com.xiaoe.xiaoegeneral.model.ImageItem
import me.drakeet.multitype.ItemViewBinder

class MainActivityImageBinder() : ItemViewBinder<ImageItem, MainActivityImageBinder.ImageHolder>() {
    override fun onBindViewHolder(holder: ImageHolder, item: ImageItem) {
        val lp = holder.itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams
        lp?.height = ScreenUtils.getScreenHeight() / 2
        lp?.let { holder.itemView.layoutParams = it }

        holder.image.show(item.url)
        holder.image.setOnClickListener {
            holder.image.zoom(item.url)
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ImageHolder {
        return ImageHolder(inflater.inflate(R.layout.image_item, parent, false))
    }

    fun getLayoutId() = R.layout.image_item

    inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv)
    }
}