package com.xiaoe.common.utils.widget

import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.xiaoe.common.base.ui.IList
import com.xiaoe.common.base.ui.IStateView
import me.drakeet.multitype.MultiTypeAdapter

//当前适用SmartRefreshLayout、MultiTypeAdapter管理刷新、加载、页数
class RefreshManager : IList {

    val DEFAULT_SIZE = 10
    val DEFAULT_START_OFFSET = 0

    private var loadSize = DEFAULT_SIZE//每次加载条目
    private var pageStartOffset = DEFAULT_START_OFFSET//起始页
    private var totalPage = -1//加载总页数
    private var currentPage = pageStartOffset//当前加载页

    private var iStateView: IStateView? = null
    private var srl: SmartRefreshLayout? = null
    private var mta: MultiTypeAdapter? = null


    /**
     * 加载总页数，通常由接口获取，没有可不传，则加载更多由下一次加载的loadSize来判断
     */
    fun setTotalPage(totalPage: Int): RefreshManager {
        this.totalPage = totalPage
        return this
    }

    /**
     * 每次加载条目数，【必须与保持接口一致】!用于在未传totalPage之后判断是否加载更多
     */
    fun setLoadSize(size: Int): RefreshManager {
        this.loadSize = size
        return this
    }

    /**
     * 起始加载偏移页
     */
    fun setPageStartOffset(start: Int): RefreshManager {
        this.pageStartOffset = start
        return this
    }

    /**
     * 管理外部的View切换
     */
    fun setIStateView(iStateView: IStateView): RefreshManager {
        this.iStateView = iStateView
        return this
    }

    /**
     * 设置刷新View
     */
    fun setSmartRefreshLayout(srl: SmartRefreshLayout?): RefreshManager {
        this.srl = srl
        return this
    }

    /**
     * 刷新监听，【必须置于最后】
     */
    fun setOnRefreshListener(listener: (startOffset: Int) -> Unit): RefreshManager {
        srl?.setOnRefreshListener { listener.invoke(pageStartOffset) }
        return this
    }

    /**
     * 加载更多监听，必须置于最后】
     * ①有传totalPage
     * ②没有totalPage，用loadSize判断
     */
    fun setOnLoadMoreListener1(listener: (nextPage: Int) -> Unit): RefreshManager {
        srl?.setOnLoadMoreListener { listener.invoke(getNextPage()) }
        return this
    }

    /**
     * 加载更多监听，必须置于最后】，返回最后一个item
     */
    fun <T> setOnLoadMoreListener2(listener: (lastItem: T) -> Unit): RefreshManager {
        srl?.setOnLoadMoreListener { listener.invoke(mta!!.items.last() as T) }
        return this
    }

    /**
     * 设置适配器
     */
    fun setAdapter(mta: MultiTypeAdapter?): RefreshManager {
        this.mta = mta
        return this
    }

    /**
     * 获取下一次加载的page
     */
    fun getNextPage() = currentPage + 1

    override fun setData(beanList: List<Any>, loadMore: Boolean) {
        if (beanList == null || beanList.isEmpty()) {
            //refresh trigger
            if (!loadMore) {
                iStateView?.showEmpty()
                srl?.finishRefresh()
            } else {
                srl?.finishLoadMore()
            }
            return
        }
        //refresh trigger
        if (!loadMore) {
            mta?.items = beanList
            mta?.notifyDataSetChanged()
            currentPage = pageStartOffset
            iStateView?.showContent()
            srl?.finishRefresh()
            srl?.setEnableLoadMore(true)
        } else {
            //没有传递totalPage，（验证发生在下次加载时）
            if (totalPage == -1 || totalPage == 0) {
                val valid = beanList.size >= loadSize

                (mta?.items as? ArrayList)?.addAll(beanList)
                mta?.notifyItemRangeInserted(mta?.items!!.size - beanList.size, beanList.size)

                currentPage++
                if (valid) {
                    srl?.finishLoadMore()
                } else {
                    srl?.finishLoadMore()
                    srl?.setEnableLoadMore(false)
                }
                return
            }
            //有传递totalPage，（验证发生在这次加载后）
            if (currentPage < totalPage + pageStartOffset - 1) {

                (mta?.items as? ArrayList)?.addAll(beanList)
                mta?.notifyItemRangeInserted(mta?.items!!.size - beanList.size, beanList.size)

                srl?.finishLoadMore()
                currentPage++
                srl?.setEnableLoadMore(currentPage < totalPage + pageStartOffset - 1)
            } else {
                srl?.finishLoadMore()
                srl?.setEnableLoadMore(false)
            }
        }
    }

    override fun setMessage(error: Any, content: String) {
        if (mta?.items?.isEmpty() == true) {
            iStateView?.showMessageFromNet(error, content)
            srl?.finishRefresh()
            srl?.finishLoadMore()
        } else {
            iStateView?.showMessage(content)
            srl?.finishRefresh()
            srl?.finishLoadMore()
        }
    }
}