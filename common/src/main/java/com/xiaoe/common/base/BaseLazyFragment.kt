
package com.xiaoe.common.base

import android.os.Bundle
import android.view.View
import com.trello.rxlifecycle3.components.support.RxFragment

abstract class BaseLazyFragment : RxFragment() {

    private var isFirstResume = true
    private var isFirstVisible = true
    private var isFirstInvisible = true
    private var isPrepared = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPrepare()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInvisible()
        }
    }
    //仅使用FragmentPagerAdapter时调用。才能实现懒加载。
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    //首次可见
    // 【注意：此方法需要setUserVisibleHint被回调才可以使用。所以加载fragment应该统一使用viewpager+FragmentPagerAdapter加载】
    //快速设置ViewPager，可使用com.xiaoe.common.ext.ext.UtilExtKt.setupAdapter扩展
    protected abstract fun onFirstUserVisible()

    //每次可见
    open fun onUserVisible() {}

    //首次不可见
    open fun onFirstUserInvisible() {}

    //每次不可见
    open fun onUserInvisible() {}

    @Synchronized private fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }
}
