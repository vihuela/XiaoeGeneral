package com.xiaoe.app2

import android.os.Bundle
import com.xiaoe.app2.binder.MainActivityImageBinder
import com.xiaoe.app2.databinding.App2ActivityMainBinding
import com.xiaoe.app2.presenter.MainActivityPresenter
import com.xiaoe.app2.request.Api
import com.xiaoe.common.base.BaseActivity
import com.xiaoe.common.base.ui.IList
import com.xiaoe.common.ext.ext.applyStatusBarBlack
import com.xiaoe.common.utils.widget.RefreshManager
import kotlinx.android.synthetic.main.app2_activity_main.*
import me.drakeet.multitype.MultiTypeAdapter

class MainActivity : BaseActivity<MainActivityPresenter, App2ActivityMainBinding>(),
    IList {
    override fun setData(beanList: List<Any>, loadMore: Boolean) {
        refreshMgr?.setData(beanList, loadMore)
    }

    override fun setMessage(error: Any, content: String) {
        refreshMgr?.setMessage(error, content)
    }

    var adapter: MultiTypeAdapter? = null
    var refreshMgr: RefreshManager? = null
    override fun getLayoutId(): Int = R.layout.app2_activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        adapter = MultiTypeAdapter()
        adapter!!.register(MainActivityImageBinder())

        mRecycleView.also {
            it.adapter = adapter
        }

        refreshMgr = RefreshManager()
            .setSmartRefreshLayout(refreshLayout)
            .setAdapter(adapter)
            .setIStateView(this)
            .setLoadSize(Api.pageSize)
            .setPageStartOffset(Api.startOffset)
            .setOnRefreshListener {
                mPresenter.getNewsList(this)
            }
            .setOnLoadMoreListener1 { nextPage ->
                mPresenter.getNewsListForDate(this, nextPage)
            }

        refreshLayout.autoRefresh()

    }

    override fun onStateViewRetryListener() {
        super.onStateViewRetryListener()
        refreshLayout.autoRefresh()
    }
}

