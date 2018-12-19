package com.aaron.demo.common.base.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.aaron.demo.common.base.activity.immersive.StatusBarManager
import me.imid.swipebacklayout.lib.app.SwipeBackActivity


/**
 * Created on 2018/4/3.
 * @author HuangRan
 * @version
 */
abstract class BaseActivity : SwipeBackActivity() {
    val tag = javaClass.simpleName
    lateinit var statusBarMessage: StatusBarManager

    companion object {
        var isForeground = false // app是否处于前台激活状态
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarMessage = StatusBarManager(this)
    }

    fun launchFragmentByAdd(layoutId: Int, fragment: Fragment) =
            supportFragmentManager.beginTransaction().add(layoutId, fragment).commit()

    fun launchFragmentByReplace(layoutId: Int, fragment: Fragment) =
            supportFragmentManager.beginTransaction().replace(layoutId, fragment).commit()

    fun <T : Activity> startActivity(cls: Class<T>) = startActivity(Intent(this, cls))

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.d(tag, "onWindowFocusChanged")

    }

    protected open fun isEventTarget(): Boolean = false

    override fun onResume() {
        super.onResume()
        Log.d(tag, "- onResume -")
        isForeground = true
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "- onPause -")
        isForeground = false
    }

}