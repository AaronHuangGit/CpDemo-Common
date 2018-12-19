package com.aaron.demo.common.base.activity.appbar

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.aaron.demo.common.R
import kotlinx.android.synthetic.main.layout_app_bar.view.*

/**
 * Created on 15/6/26.
 *
 * @author ran.huang
 * @version 1.0.0
 *
 *  * 构造器
 * @param context 上下文资源
 */
class AppBarController(val context: Context) {
    companion object {
        private const val ACTION_ITEM_MAX_COUNT = 2
    }

    val view: View = LayoutInflater.from(context)
            .inflate(R.layout.layout_app_bar, null, false)
    val toolbar: Toolbar
        get() = view.findViewById(R.id.toolbar)
    val titleTextView: TextView
        get() = view.findViewById(R.id.titleTextView)

    /**
     * 自定义Toolbar内容View
     * @param view View
     */
    fun setCustomView(view: View) {
        toolbar.removeAllViews()
        toolbar.addView(view)
    }

    fun setTitle(title: CharSequence?) {
        titleTextView.text = title
    }

    //todo 后面这里需要创建一个ActionItem类，封装所有信息，以及Build模式创建
    fun addActionItem(actionItemType: ActionItemType, resId: Int, onClickListener: View.OnClickListener) {
        if (view.actionItemLinearLayout.childCount == ACTION_ITEM_MAX_COUNT) {
            return
        }
        val layoutParams: LinearLayout.LayoutParams
        val size = context.resources.getDimension(R.dimen.appToolbarActionItemSize).toInt()
        layoutParams = if (actionItemType == ActionItemType.Image) {
            LinearLayout.LayoutParams(size, size)
        } else {
            LinearLayout.LayoutParams(-2,-2)
        }
        layoutParams.gravity = Gravity.CENTER
        if (view.actionItemLinearLayout.childCount > 0) {
            layoutParams.marginStart = context.resources.getDimension(R.dimen.appToolbarActionItemMarginStart).toInt()
        }
        view.actionItemLinearLayout.addView(buildActionItem(actionItemType, resId, onClickListener), layoutParams)
    }

    fun addImageActionItem(drawableResId: Int, onClickListener: View.OnClickListener) = addActionItem(ActionItemType.Image, drawableResId, onClickListener)

    fun addTextActionItem(resId: Int, onClickListener: View.OnClickListener) = addActionItem(ActionItemType.Text, resId, onClickListener)

    private fun buildActionItem(actionItemType: ActionItemType, resId: Int, onClickListener: View.OnClickListener): View =
            when (actionItemType) {
                ActionItemType.Image -> buildActionImageItem(resId, onClickListener)
                ActionItemType.Text -> buildActionTextItem(resId, onClickListener)
            }

    private fun buildActionImageItem(drawableResId: Int, onClickListener: View.OnClickListener): View = ImageView(view.context).run {
        setImageResource(drawableResId)
        setOnClickListener(onClickListener)
        return this
    }

    private fun buildActionTextItem(resId: Int, onClickListener: View.OnClickListener): View = TextView(view.context).run {
        setText(resId)
        setTextColor(Color.WHITE)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimensionPixelOffset(R.dimen.appToolbarActionItemTextSize).toFloat())
        gravity = Gravity.CENTER
        setOnClickListener(onClickListener)
        return this
    }

    enum class ActionItemType {
        Image,
        Text
    }

}
