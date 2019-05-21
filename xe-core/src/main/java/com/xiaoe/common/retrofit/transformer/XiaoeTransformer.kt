@file:Suppress("UNCHECKED_CAST")

package com.xiaoe.common.retrofit.transformer

import com.xiaoe.common.ext.eventbus.Event
import com.xiaoe.common.ext.eventbus.sendEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.rx_cache2.Reply
import com.xiaoe.common.resource.ACCOUNT_CONFILICT
import com.xiaoe.common.resource.ACCOUNT_UNLOGIN
import com.xiaoe.common.resource.CERTIFI_AUTHING
import com.xiaoe.common.resource.CERTIFI_UN_AUTH
import com.xiaoe.common.retrofit.exception.XiaoeBusinessException
import com.xiaoe.common.retrofit.request.BaseRequest

/**
 * 带业务性质的网络过滤 todo 业务定制
 */
class XiaoeTransformer<T> : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        val a = upstream.flatMap {
            when (it) {
                //normal
                is BaseRequest<*> -> {
                    val b = it as BaseRequest<*>
                    if (b.res == 0) {
                        Observable.just(it)
                    } else {
                        handleError(b)
                    }
                }
                //rxCache
                is Reply<*> -> {
                    val b = (it as Reply<*>).data as? BaseRequest<*>
                    if (b != null) {
                        if (b.res == 0) {
                            Observable.just(it)
                        } else {
                            handleError(b)
                        }
                    } else {
                        Observable.just(it)
                    }
                }
                else -> {
                    Observable.just(it)
                }
            }
        }
        return a as ObservableSource<T>
    }

    private fun handleError(b: BaseRequest<*>): Observable<Any?>? {
        sendEvent(
            Event.obtain(
                when (b.res) {
                    420 -> ACCOUNT_CONFILICT//冲突
                    401 -> ACCOUNT_UNLOGIN//未登录
                    103 -> CERTIFI_UN_AUTH//未认证
                    104 -> CERTIFI_AUTHING//正在认证中
                    else -> -1
                }, b.msg
            )
        )
        return Observable.error(XiaoeBusinessException(b.msg, b.res))
    }

}