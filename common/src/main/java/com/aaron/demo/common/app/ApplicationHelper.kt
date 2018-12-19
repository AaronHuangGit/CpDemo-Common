package com.aaron.demo.common.app

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.facebook.common.util.ByteConstants
import com.soybean.framework.imageloader.ImageCacheParams
import com.soybean.framework.imageloader.ImageLoader
import com.yueren.util.DisplayUtils

/**
 * Created on 2018/5/9.
 * @author HuangRan
 * @version
 */

object ApplicationHelper {
    private val TAG = javaClass.simpleName

    fun initImageLoader(context: Context?) {
        val imageCacheParams = ImageCacheParams()
        imageCacheParams.maxMemoryCacheSize = getMaxCacheSize(context)
        ImageLoader.instance.initialize(context, imageCacheParams)
    }

    fun printInfo() {
        Log.d(TAG, "\n---ApkInfo---\nappFlag: " + AppConfig.appFlag + "\nappVersionName: " + AppConfig.appVersionName +
                "\nappVersionCode: " + AppConfig.appVersionCode + "\nhostUrl" + AppConfig.hostUrl)
        Log.d(TAG, "\n---ScreenInfo---\nscreenWidth: " + DisplayUtils.widthPixels + "\nscreenHeight: " + DisplayUtils.heightPixels
                + "\ndensityDpi: " + DisplayUtils.densityDpi + "\ndensity: " + DisplayUtils.density + "\ndensityStr: " + DisplayUtils.bitmapDensityStr)
    }

    private fun getMaxCacheSize(context: Context?): Int {
        val maxMemory = Math.min((context!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .memoryClass * ByteConstants.MB, Int.MAX_VALUE)
        val memory: Int
        memory = when {
            maxMemory < 32 * ByteConstants.MB -> 4 * ByteConstants.MB
            maxMemory < 64 * ByteConstants.MB -> 6 * ByteConstants.MB
            else -> maxMemory / 8
        }
        Log.d("MainProcessInit", "memory: " + memory / ByteConstants.MB + "M")
        return memory
    }
}