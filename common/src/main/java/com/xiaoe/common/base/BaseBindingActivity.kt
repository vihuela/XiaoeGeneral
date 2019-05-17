
package com.xiaoe.common.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.xiaoe.common.base.ui.IView
import com.xiaoe.common.base.utils.PresenterFactory
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

/**
 * no presenter use {@link EmptyPresenter}
 */
abstract class BaseBindingActivity<T : BasePresenter<*>, B : ViewDataBinding> : RxAppCompatActivity(), IView {

    protected lateinit var mBinding: B
    protected lateinit var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = createViewDataBinding()
        mPresenter = PresenterFactory.createPresenter(this)
    }

    private fun createViewDataBinding(): B = DataBindingUtil.setContentView(this, getLayoutId())

    abstract fun getLayoutId(): Int

}
