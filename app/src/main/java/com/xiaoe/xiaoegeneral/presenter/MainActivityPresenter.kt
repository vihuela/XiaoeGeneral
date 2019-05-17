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

        val api = Api.IMPL.getImageList(Api.pageSize, page)
        val cacheApi = ApiCacheProvider.IMPL.getImageList(api, DynamicKey(page))

        api//可以切换api和cacheApi
            .defPolicy_Retry(this,"getImageList")
            .compose(XiaoeTransformer())
            .map {
                //cacheApi
                it.results.map { ImageItem(it.url) }
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