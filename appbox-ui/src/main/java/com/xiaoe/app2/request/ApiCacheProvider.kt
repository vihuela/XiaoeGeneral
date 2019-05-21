package com.xiaoe.app2.request

import com.blankj.utilcode.util.NetworkUtils
import com.xiaoe.app2.request.model.NewsRequest
import com.xiaoe.common.retrofit.ApiUtils
import io.reactivex.Observable
import io.rx_cache2.*
import java.util.concurrent.TimeUnit

interface ApiCacheProvider {
    companion object {
        val IMPL: ApiCacheProvider = ApiUtils.getApiCache(ApiCacheProvider::class.java)
        val NO_CACHE =  NetworkUtils.isConnected()//网络连接时不使用缓存
    }

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getNewsList(O: Observable<NewsRequest.ListRes>, evictProvider: EvictProvider = EvictProvider(NO_CACHE)): Observable<Reply<NewsRequest.ListRes>>

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getNewsListForDate(O: Observable<NewsRequest.ListRes>, url: DynamicKey, evictDynamicKey: EvictDynamicKey = EvictDynamicKey(NO_CACHE)): Observable<Reply<NewsRequest.ListRes>>

}