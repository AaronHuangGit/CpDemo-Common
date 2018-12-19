package com.aaron.demo.common.base.activity.appbar

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.aaron.demo.common.base.activity.BaseActivity
import kotlinx.android.synthetic.main.layout_app_bar.view.*

/**
 * Created on 2018/6/20.
 * @author HuangRan
 * @version
 */

abstract class AppBarActivity : BaseActivity() {
    private lateinit var rootView: LinearLayout
    private lateinit var appBarController: AppBarController
    private lateinit var contentView: FrameLayout
    protected var homeIconClickListener: View.OnClickListener? = DefaultHomeIconClickListener()
        set(value) {
            rootView.homeButton.setOnClickListener(value)
        }

    protected val toolbar
        get() = appBarController.toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        super.setContentView(rootView)
    }

    private fun init() {
        initRootView()
        initToolBar()
        initContentView()
    }

    private fun initContentView() {
        contentView = FrameLayout(this)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(-1, -1)
        rootView.addView(contentView, layoutParams)
    }

    private fun initToolBar() {
        appBarController = AppBarController(this)
        rootView.addView(appBarController.view)
        setSupportActionBar(appBarController.toolbar)
        rootView.homeButton.setOnClickListener(homeIconClickListener)
    }

    private fun initRootView() {
        rootView = LinearLayout(this)
        rootView.orientation = LinearLayout.VERTICAL
    }

    protected fun hideAppBar() = supportActionBar!!.hide()

    override fun setTitle(titleId: Int) = appBarController.setTitle(getString(titleId))

    override fun setTitle(title: CharSequence?) = appBarController.setTitle(title)

    override fun setContentView(view: View) {
        if (contentView.childCount > 0) {
            contentView.removeAllViews()
        }
        contentView.addView(view)
    }

    override fun setContentView(layoutResID: Int) {
        val view = layoutInflater.inflate(layoutResID, null, false)
        val layoutParams = RelativeLayout.LayoutParams(-1, -1)
        contentView.addView(view, layoutParams)
    }

    protected fun setCustomView(view: View) =
            appBarController.setCustomView(view)

    protected fun addActionItem(actionItemType: AppBarController.ActionItemType,
                                drawableResId: Int,
                                onClickListener: View.OnClickListener) =
            appBarController.addActionItem(actionItemType, drawableResId, onClickListener)

    protected fun addImageActionItem(drawableResId: Int,
                                     onClickListener: View.OnClickListener) =
            appBarController.addImageActionItem(drawableResId, onClickListener)

    protected fun addTextActionItem(resId: Int,
                                    onClickListener: View.OnClickListener) =
            appBarController.addTextActionItem(resId, onClickListener)

    inner class DefaultHomeIconClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            onBackPressed()
        }
    }

    fun hiddenLeftButton(visibility: Int) {
        rootView.homeButton.visibility = visibility;
    }

    fun showHomeText(resId: Int? = null, onClickListener: View.OnClickListener? = null) {
        rootView.homeButton.visibility = View.GONE
        rootView.homeTextView.visibility = View.VISIBLE
        resId?.let {
            rootView.homeTextView.text = getString(it)
        }
        rootView.homeTextView.setOnClickListener(onClickListener ?: DefaultHomeIconClickListener())
    }

}