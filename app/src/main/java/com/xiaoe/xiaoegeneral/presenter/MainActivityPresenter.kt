package com.xiaoe.xiaoegeneral.presenter

import com.xiaoe.common.base.BasePresenter
import com.xiaoe.common.ext.ext.defPolicy_Retry
import com.xiaoe.common.ext.ext.parse
import com.xiaoe.common.base.ui.IList
import com.xiaoe.common.retrofit.transformer.XiaoeTransformer
import com.xiaoe.xiaoegeneral.model.ImageItem
import com.xiaoe.xiaoegeneral.request.Api
import com.xiaoe.xiaoegeneral.request.ApiCacheProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.rx_cache2.DynamicKey

class MainActivityPresenter : BasePresenter<IList>() {

    fun getImageList(view: IList, page: Int = Api.startOffset, loadMore: Boolean) {
        //正常retrofit
        val api = Api.IMPL.getImageList(Api.pageSize, page)
        //拥有缓存配置retrofit
        val cacheApi = ApiCacheProvider.IMPL.getImageList(api, DynamicKey(page))

        cacheApi
            .defPolicy_Retry(this,"getImageList")//第二个参数作用：比如连续发起10个请求，会取消前面9个
            .compose(XiaoeTransformer())//数据源生产者的通用过滤
            .map {
                //cacheApi
                it.data.results.map { ImageItem(it.url) }
                //api
                /*it.results.map { ImageItem(it.url) }*/
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.setData(it, loadMore)
            },
                {
                    it.parse { error, message ->
                        view.setMessage(error, message)
                    }
                })
    }
}