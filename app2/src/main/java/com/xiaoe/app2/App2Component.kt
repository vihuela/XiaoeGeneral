package com.xiaoe.app2

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.billy.cc.core.component.IComponent

class App2Component : IComponent{
    override fun onCall(cc: CC): Boolean {
        when(cc.actionName){
            "showNewsPage"->{
                CCUtil.navigateTo(cc, MainActivity::class.java)
                CC.sendCCResult(cc.callId, CCResult.success())
            }
        }
        return false
    }

    override fun getName(): String = "App2Component"
}