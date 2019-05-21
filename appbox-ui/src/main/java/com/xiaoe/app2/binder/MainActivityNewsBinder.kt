package com.xiaoe.app2.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xiaoe.app2.R
import com.xiaoe.app2.request.model.NewsRequest
import com.xiaoe.common.ext.ext.show
import me.drakeet.multitype.ItemViewBinder

class MainActivityNewsBinder() : ItemViewBinder<NewsRequest.ListRes.StoriesBean, MainActivityNewsBinder.ImageHolder>() {
    override fun onBindViewHolder(holder: ImageHolder, item: NewsRequest.ListRes.StoriesBean) {

        holder.image.show(item.getImageUrl())
        holder.title.text = item.title
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ImageHolder {
        val imageHolder = ImageHolder(inflater.inflate(R.layout.abu_item_nomal_story, parent, false))
        return imageHolder
    }


    inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_view)
        val title: TextView = itemView.findViewById(R.id.tv_title)
    }
}