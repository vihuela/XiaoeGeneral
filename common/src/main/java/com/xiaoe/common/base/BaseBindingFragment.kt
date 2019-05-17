
package com.xiaoe.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.xiaoe.common.base.ui.IView
import com.xiaoe.common.base.utils.PresenterFactory

/**
 * no presenter use {@link EmptyPresenter}
 */
abstract class BaseBindingFragment<T : BasePresenter<*>, B : ViewDataBinding> : BaseLazyFragment(), IView {

    protected lateinit var mBinding: B
    protected lateinit var mPresenter: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mPresenter = PresenterFactory.createPresenter(this)
        return mBinding.root
    }

    abstract fun getLayoutId(): Int

}
