package com.aaron.demo.common.base.widget.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * Created on 15/5/26.
 *
 * @author HuangRan
 */
abstract class BaseListAdapter<T>(
        val context: Context) : BaseAdapter() {

    var dataList: MutableList<T> = mutableListOf()
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    protected abstract fun createViewHolder(): BaseViewHolder<T>

    /**
     * 设置数据
     * @param list List<T>
    </T> */
    fun setData(list: MutableList<T>?) {
        dataList = list?.toMutableList() ?: mutableListOf()
    }

    /**
     * 设置数据并且通知更新
     * @param list List<T>
    </T> */
    fun setDataAndNotifyChanged(list: MutableList<T>) {
        setData(list)
        notifyDataSetChanged()
    }

    /**
     * 叠加数据
     * @param list List<T>
    </T> */
    fun addData(list: MutableList<T>) {
        if (isEmptyList(list)) {
            return
        }
        dataList.addAll(list)
    }

    /**
     * 叠加数据并且通知更新
     * @param list List<T>
    </T> */
    fun addDataAndNotifyChanged(list: MutableList<T>) {
        addData(list)
        notifyDataSetChanged()
    }


    /**
     * 移除数据
     * @param position 数据索引
     */
    fun remove(position: Int) {
        if (dataList.size > position) {
            dataList.removeAt(position)
        }
    }

    /**
     * 移除数据并且通知更新
     * @param position 数据索引
     */
    fun removeAndNotifyChanged(position: Int) {
        if (dataList.size > position) {
            dataList.removeAt(position)
        }
        notifyDataSetChanged()
    }

    private fun isEmptyList(list: MutableList<T>): Boolean {
        return list.isEmpty()
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): T {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val data = dataList[position]
        val viewHolder: BaseViewHolder<T>
        if (convertView == null) {
            viewHolder = createViewHolder()
            viewHolder.parentView = parent
            view = viewHolder.itemView
        } else {
            viewHolder = convertView.tag as BaseViewHolder<T>
        }
        viewHolder.position = position
        viewHolder.bindViews(data)
        return view!!
    }


    /**
     * Created on 15/7/4.
     *
     * @author ran.huang
     * @version 1.0.0
     * @param <T> 泛型
    </T> */
    abstract class BaseViewHolder<in T>(var itemView: View) {
        var position: Int = 0
        var parentView: ViewGroup? = null

        init {
            this.inflateItemView()
            itemView.tag = this
        }

        /**
         * 创建ItemView
         */
        abstract fun inflateItemView()

        /**
         * 刷新内部控件
         * @param obj T
         */
        abstract fun bindViews(obj: T)
    }
}
