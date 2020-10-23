package com.hearing.aitchat.selector

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hearing.aitchat.R

/**
 * @author liujiadong
 * @since 2020/10/21
 */
class SelectorPopupView(context: Context) {

    companion object {
        const val SELECTOR_TYPE_USER = 0
        const val SELECTOR_TYPE_TOPIC = 1
    }

    private var popupWindow: PopupWindow? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: DataAdapter? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_selector_list, null)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = DataAdapter()
        recyclerView?.adapter = adapter
        popupWindow = PopupWindow(view, 600, 800)
        popupWindow?.isOutsideTouchable = true
    }

    fun setDataChooseListener(listener: DataAdapter.OnDataChooseListener) {
        adapter?.onDataChooseListener = listener
    }

    fun show(view: View, type: Int = SELECTOR_TYPE_USER, key: String? = null) {
        when (type) {
            SELECTOR_TYPE_USER -> adapter?.setData(UserFactory.getUsersByKey(key))
            SELECTOR_TYPE_TOPIC -> adapter?.setData(TopicFactory.getTopicsByKey(key))
        }
        popupWindow?.showAtLocation(view, Gravity.CENTER, -100, -500)
    }

    fun dismiss() {
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        }
    }
}