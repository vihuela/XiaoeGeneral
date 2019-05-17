
package com.xiaoe.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiaoe.common.base.ui.IView
import com.xiaoe.common.base.utils.BehaviorMap
import com.trello.rxlifecycle3.components.support.RxFragment

/**
 *
 * <h3>Build Presenter with Fragment：</h3>
 *
 * Fragment has a life cycle
 *
 * FragmentManager can cache fragment
 *
 * Coordinate with RxLifeCycle
 *
 */
abstract class BasePresenter<V : IView> : RxFragment() {
    var mView: V? = null//如果多个View绑定一个Presenter时。注意获取的View为最后一个
    val mBehaviorMap by lazy { BehaviorMap() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView != null)
            onViewCreated(mView!!, arguments, savedInstanceState)
        return null
    }

    fun onViewCreated(view: V, arguments: Bundle?, savedInstanceState: Bundle?){}

    //abandon
    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun view(): V = mView!!

    fun setView(view: Any) {
        this.mView = view as V
    }

}
