

package com.xiaoe.xiaoegeneral.request.model

class ImageRequest {
    val error: Boolean = false
    val results: List<Item> = arrayListOf()

    data class Item(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
    )
}