package com.xiaoe.xiaoegeneral

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.billy.cc.core.component.CC
import com.blankj.utilcode.util.SizeUtils
import com.xiaoe.app_base.ComponentRes
import com.xiaoe.app_base.model.UserInfo
import com.xiaoe.common.base.BaseActivity
import com.xiaoe.common.base.defaults.EmptyPresenter
import com.xiaoe.common.ext.ext.applyStatusBarBlack
import com.xiaoe.common.base.ui.IList
import com.xiaoe.common.ext.ext.setupAdapter
import com.xiaoe.common.utils.widget.RefreshManager
import com.xiaoe.xiaoegeneral.binder.MainActivityImageBinder
import com.xiaoe.xiaoegeneral.databinding.ActivityMainBinding
import com.xiaoe.xiaoegeneral.presenter.MainActivityPresenter
import com.xiaoe.xiaoegeneral.request.Api
import com.xiaoe.xiaoegeneral.ui.ImageFragment
import com.xiaoe.xiaoegeneral.utils.GridItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import me.drakeet.multitype.MultiTypeAdapter

class MainActivity : BaseActivity<EmptyPresenter, ActivityMainBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()

        //组件化获取其它模块fragment
        val newFragment = CC.obtainBuilder(ComponentRes.app2ComponentName)
            .setActionName(ComponentRes.app2Component_getFragment)
            .setParams(mutableMapOf(Pair<String,Any>("action","fromApp1")))
            .build()
            .call()
            .getDataItemWithNoKey<Fragment>()

        pager.setupAdapter(supportFragmentManager, ImageFragment(), newFragment)

        //组件化拉起
        titleTv.setOnClickListener {
            CC.obtainBuilder(ComponentRes.app2ComponentName)
                .setActionName(ComponentRes.app2Component_showNewActivity)
                .build().callAsyncCallbackOnMainThread { cc, result ->
                    val para1 = result.dataMap["user"] as UserInfo
                    val para2 = result.dataMap["param2"]
                    println()
                }
        }
    }

}
