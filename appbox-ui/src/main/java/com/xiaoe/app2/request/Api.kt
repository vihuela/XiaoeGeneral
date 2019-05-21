
package com.xiaoe.app2.request

import com.xiaoe.app2.request.model.NewsRequest
import com.xiaoe.common.retrofit.ApiUtils
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    companion object {
        val listConfig = mapOf(
                Pair("pageSize", 10),
                Pair("startOffset", 1))
                .withDefault { 0 }

        val IMPL: Api = ApiUtils.get(Api::class.java)
        val pageSize by listConfig
        val startOffset by listConfig
    }

    @GET("http://news-at.zhihu.com/api/4/news/latest")
    fun getNewsList(): Observable<NewsRequest.ListRes>

    //the last day of the current date
    @GET("http://news-at.zhihu.com/api/4/news/before/{date}")
    fun getNewsListForDate(@Path("date") date: String): Observable<NewsRequest.ListRes>

}