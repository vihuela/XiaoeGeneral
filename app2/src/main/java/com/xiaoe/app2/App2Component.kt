package com.xiaoe.app2

import android.os.Bundle
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.billy.cc.core.component.IComponent
import com.xiaoe.app2.ui.NewsFragment
import com.xiaoe.app_base.ComponentRes

class App2Component : IComponent {
    override fun onCall(cc: CC): Boolean {
        when (cc.actionName) {
            ComponentRes.app2Component_showNewActivity -> {
                CCUtil.navigateTo(cc, MainActivity::class.java)
            }

            ComponentRes.app2Component_getFragment -> {
                val newsFragment = NewsFragment().apply {
                    val k = "action"
                    arguments = Bundle().also { it.putString(k, cc.getParamItem(k)) }
                }
                CC.sendCCResult(cc.callId, CCResult.successWithNoKey(newsFragment))
            }
        }
        return true
    }

    override fun getName(): String = ComponentRes.app2ComponentName
}