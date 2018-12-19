package com.aaron.demo.common.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.aaron.demo.common.BuildConfig
import com.aaron.demo.common.store.DiskStorageManager
import com.alibaba.android.arouter.launcher.ARouter
import com.yueren.util.DisplayUtils
import com.yueren.util.NetworkUtils


open class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var application: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        initProcess()
        ApplicationHelper.printInfo()
    }

    private fun initProcess() {
//        Preference.init(this)
        AppConfig.init(AppConfig.AppConfigData(BuildConfig.APP_FLAG,
                BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, BuildConfig.TEST_MODE, BuildConfig.HTTP_HOST))
        if (AppConfig.isTestMode == true) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
        NetworkUtils.init(this)
        ApplicationHelper.initImageLoader(this)
        DiskStorageManager.init(AppConfig.appFlag)
        DisplayUtils.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}