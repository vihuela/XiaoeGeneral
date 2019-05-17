package com.xiaoe.common.ext.impl

import android.app.Activity
import android.app.Application
import com.blankj.utilcode.util.Utils
import com.xiaoe.common.BuildConfig
import com.xiaoe.common.resource.DataStatistics
import com.xiaoe.common.retrofit.ApiPath
import com.xiaoe.common.retrofit.ApiUtils
import io.paperdb.Paper

interface IApp : INetState, ICurActivity {

    fun isDevEnvironment() = BuildConfig.DEBUG

    fun commonInit(application: Application,
                   currentActivity: (activity: Activity) -> Unit,//当前的activity
                   filterPager: ArrayList<String>) {//不使用断网提示的页面，传activity.javaClass.simpleName
        //utils
        Utils.init(application)
        //db
        Paper.init(application)
        //environment
        ApiPath.setEnvironment(if (isDevEnvironment()) ApiPath.currentEnvir else ApiPath.Formal)
        //retrofit
        ApiUtils.init(application, isDevEnvironment())
        //statistic
        DataStatistics.close = isDevEnvironment()
        //current activity
        observeCurActivity(application) { currentActivity.invoke(it) }
        //observe network
        observeNetwork(application, { ApiUtils.isRxCacheEvict = it }, filterPager)
    }

}