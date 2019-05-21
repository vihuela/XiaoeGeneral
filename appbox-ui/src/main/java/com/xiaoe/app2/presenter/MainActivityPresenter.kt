package com.xiaoe.app2.presenter

import com.xiaoe.common.base.BasePresenter
import com.xiaoe.common.base.ui.IList
import com.xiaoe.app2.request.Api
import com.xiaoe.app2.request.ApiCacheProvider
import com.xiaoe.common.ext.ext.defPolicy_Retry
import com.xiaoe.common.ext.ext.parse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.rx_cache2.DynamicKey
import java.text.SimpleDateFormat
import java.util.*

class MainActivityPresenter : BasePresenter<IList>() {

    fun getNewsList(view: IList) {

        val api = Api.IMPL.getNewsList()
        ApiCacheProvider.IMPL.getNewsList(api)
            .defPolicy_Retry(this)
            .map { it.data.stories }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.setData(it, false) },
                { it.parse { error, message -> view.setMessage(error, message) } })
    }

    fun getNewsListForDate(view: IList, page: Int = 1) {

        val dateString = getNextDay(-1 * page)
        val api = Api.IMPL.getNewsListForDate(dateString)
        ApiCacheProvider.IMPL.getNewsListForDate(api, DynamicKey(page))
            .defPolicy_Retry(this)
            .map { it.data.stories }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view.setData(it, true) },
                { it.parse { error, message -> view.setMessage(error, message) } })
    }

    private fun getNextDay(delay: Int): String {
        return try {
            val format = SimpleDateFormat("yyyyMMdd", Locale.CHINESE)
            val d = Date()
            d.let { it.time = (it.time / 1000 + delay * 24 * 60 * 60) * 1000 }
            format.format(d)
        } catch (e: Exception) {
            ""
        }

    }
}