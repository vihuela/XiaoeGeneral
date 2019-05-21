package com.xiaoe.app2

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.xiaoe.common.ext.ext.getProcessNames
import com.xiaoe.common.ext.impl.IApp
import java.lang.ref.WeakReference

class App() :
    Application(), IApp {

    var mCurrentActivity: WeakReference<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        when (getProcessNames()) {
            packageName -> {
                //主进程
                commonInit(this, { mCurrentActivity = WeakReference(it) }, arrayListOf())

                //刷新库
                SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context, layout: RefreshLayout ->
                    layout.setPrimaryColorsId(R.color.app2_colorPrimary, android.R.color.white)
                    ClassicsHeader(context)
                }
                SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context, _: RefreshLayout ->
                    ClassicsFooter(context).setDrawableSize(20f)
                }
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}