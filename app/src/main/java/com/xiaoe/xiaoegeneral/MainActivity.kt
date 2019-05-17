package com.xiaoe.xiaoegeneral

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.xiaoe.common.base.BaseActivity
import com.xiaoe.common.ext.ext.applyStatusBarBlack
import com.xiaoe.common.base.ui.IList
import com.xiaoe.common.ext.eventbus.Event
import com.xiaoe.common.ext.eventbus.sendEvent
import com.xiaoe.common.ext.ext.applyStatusBarWhite
import com.xiaoe.common.utils.widget.RefreshManager
import com.xiaoe.xiaoegeneral.binder.MainActivityImageBinder
import com.xiaoe.xiaoegeneral.databinding.ActivityMainBinding
import com.xiaoe.xiaoegeneral.presenter.MainActivityPresenter
import com.xiaoe.xiaoegeneral.request.Api
import com.xiaoe.xiaoegeneral.utils.GridItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import me.drakeet.multitype.MultiTypeAdapter

class MainActivity : BaseActivity<MainActivityPresenter, ActivityMainBinding>(),
    IList {
    override fun setData(beanList: List<Any>, loadMore: Boolean) {
        refreshMgr?.setData(beanList, loadMore)
    }

    override fun setMessage(error: Any, content: String) {
        refreshMgr?.setMessage(error, content)
    }

    var adapter: MultiTypeAdapter? = null
    var refreshMgr: RefreshManager? = null
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        adapter = MultiTypeAdapter()
        adapter!!.register(MainActivityImageBinder())

        mRecycleView.also {
            it.addItemDecoration(GridItemDecoration(2, SizeUtils.dp2px(5f), false))
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            it.adapter = adapter
        }

        refreshMgr = RefreshManager()
            .setSmartRefreshLayout(refreshLayout)
            .setAdapter(adapter)
            .setIStateView(this)
            .setLoadSize(Api.pageSize)
            .setPageStartOffset(Api.startOffset)
            .setOnRefreshListener { startOffset ->  mPresenter.getImageList(this, startOffset, false) }
            .setOnLoadMoreListener { nextPage ->
                mPresenter.getImageList(this, nextPage, true)
            }

        refreshLayout.autoRefresh()

    }

    override fun onStateViewRetryListener() {
        super.onStateViewRetryListener()
        refreshLayout.autoRefresh()
    }
}
