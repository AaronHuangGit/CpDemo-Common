package com.aaron.demo.common.base.fragment

import android.support.v4.app.Fragment
import android.util.Log


/**
 * Created on 2018/4/3.
 * @author HuangRan
 * @version
 */

abstract class BaseFragment : Fragment() {
    val TAG = javaClass.simpleName
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "- onResume -")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "- onPause -")
    }

    /**
     * 是否需要加入友盟页面统计,默认所有界面都加入页面统计
     */
    open fun needStatistics(): Boolean {
        return true
    }
}