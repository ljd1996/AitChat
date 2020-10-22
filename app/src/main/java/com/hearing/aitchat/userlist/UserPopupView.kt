package com.hearing.aitchat.userlist

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
class UserPopupView(context: Context) {

    private var popupWindow: PopupWindow? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: UserAdapter? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_user_list, null)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = UserAdapter()
        recyclerView?.adapter = adapter
        popupWindow = PopupWindow(view, 600, 800)
        popupWindow?.isOutsideTouchable = true
    }

    fun setUserChooseListener(listener: UserAdapter.OnUserChooseListener) {
        adapter?.onUserChooseListener = listener
    }

    fun show(view: View, key: String?) {
        adapter?.setData(UserUtil.getUsersByKey(key))
        popupWindow?.showAtLocation(view, Gravity.CENTER, -100, -500)
    }

    fun dismiss() {
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        }
    }
}