package com.xiaoe.xiaoegeneral.request

import com.blankj.utilcode.util.NetworkUtils
import com.xiaoe.xenet.retrofit.ApiUtils
import com.xiaoe.xiaoegeneral.request.model.ImageRequest
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.LifeCache
import io.rx_cache2.Reply
import java.util.concurrent.TimeUnit

interface ApiCacheProvider {
    companion object {
        val IMPL: ApiCacheProvider = ApiUtils.getApiCache(ApiCacheProvider::class.java)
        val NO_CACHE =  NetworkUtils.isConnected()//网络连接时不使用缓存
    }

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getImageList(
        resObservable: Observable<ImageRequest>, url: DynamicKey, evictDynamicKey: EvictDynamicKey = EvictDynamicKey(
            NO_CACHE
        )
    ): Observable<Reply<ImageRequest>>

}