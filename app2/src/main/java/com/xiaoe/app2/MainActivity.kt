package com.xiaoe.app2

import android.os.Bundle
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.xiaoe.app2.databinding.App2ActivityMainBinding
import com.xiaoe.app2.ui.NewsFragment
import com.xiaoe.app_base.model.UserInfo
import com.xiaoe.common.base.BaseActivity
import com.xiaoe.common.base.defaults.EmptyPresenter
import com.xiaoe.common.ext.ext.applyStatusBarBlack
import com.xiaoe.common.ext.ext.setupAdapter
import kotlinx.android.synthetic.main.app2_activity_main.*

class MainActivity : BaseActivity<EmptyPresenter, App2ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.app2_activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        pager.setupAdapter(supportFragmentManager,NewsFragment())

        //返回调用结果给其它组件
        val result = mutableMapOf<String, Any>().also {
            it["user"] = UserInfo("ricky","man")
            it["param2"] = false
        }

        CC.sendCCResult(CCUtil.getNavigateCallId(this), CCResult.success(result))
    }
}

