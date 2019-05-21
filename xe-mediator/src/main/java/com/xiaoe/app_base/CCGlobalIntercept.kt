package com.xiaoe.app_base

import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.Chain
import com.billy.cc.core.component.IGlobalCCInterceptor

//全局拦截器
class CCGlobalIntercept : IGlobalCCInterceptor{
    override fun intercept(chain: Chain): CCResult {
//        Log.i(TAG, "============log before:" + chain.getCC())
        val result = chain.proceed()
//        Log.i(TAG, "============log after:$result")
        return result
    }

    override fun priority(): Int =1

}