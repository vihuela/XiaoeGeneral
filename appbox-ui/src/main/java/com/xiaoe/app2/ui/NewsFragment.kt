package com.xiaoe.app2.ui

import com.xiaoe.app2.R
import com.xiaoe.app2.binder.MainActivityNewsBinder
import com.xiaoe.app2.presenter.MainActivityPresenter
import com.xiaoe.app2.request.Api
import com.xiaoe.common.base.BaseFragment
import com.xiaoe.common.base.ui.IList
import com.xiaoe.common.utils.widget.RefreshManager
import kotlinx.android.synthetic.main.abu_fragment_news.*
import me.drakeet.multitype.MultiTypeAdapter

class NewsFragment : BaseFragment<MainActivityPresenter, com.xiaoe.app2.databinding.AbuFragmentNewsBinding>(), IList {


    override fun setData(beanList: List<Any>, loadMore: Boolean) {
        refreshMgr?.setData(beanList, loadMore)
    }

    override fun setMessage(error: Any, content: String) {
        refreshMgr?.setMessage(error, content)
    }

    var adapter: MultiTypeAdapter? = null
    var refreshMgr: RefreshManager? = null
    override fun getLayoutId(): Int = R.layout.abu_fragment_news

    override fun onFirstUserVisible() {
        adapter = MultiTypeAdapter()
        adapter!!.register(MainActivityNewsBinder())
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