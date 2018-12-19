package com.aaron.demo.common.base.widget.pager

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * Created on 16/3/1.
 *
 * @author aaron.huang
 * @version 1.0.0
 */
open class SimplePagerAdapter(private val views: MutableList<View>) : PagerAdapter() {

    override fun getCount(): Int {
        return views.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = views[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }
}
